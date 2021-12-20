package com.kk.vultrmanage.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kk.vultrmanage.entity.Constants;
import com.kk.vultrmanage.entity.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ServerController {
    private final Logger logger= LoggerFactory.getLogger(ServerController.class);
    private final List<Server> servers=new ArrayList<>();
    @Value("${vultr.token}")
    private String TOKEN;
    @Value("${vultr.snapshot_id}")
    private String snapshot_id;

    @RequestMapping("/list")
    @ResponseBody
    public JSONObject listServer(){
        this.getServers();
        JSONObject result=new JSONObject();

        if (servers.size()>0){
            result.put("msg","获取服务器成功!");
            result.put("servers",servers);
        }else{
            result.put("msg","没有服务器呢，可以先新建一台!");
            result.put("code",401);
        }
        return result;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public JSONObject addServer(){
        this.getServers();
        JSONObject result=new JSONObject();

        if(checkStatus()){
            result.put("msg","服务器操作中....，请稍后再试");
            result.put("code",401);
            return result;
        }

        if(servers.size()>2){
            result.put("msg","服务器添加失败，机器开的太多了，请先删除一台");
            result.put("code",401);
            return result;
        }
        JSONObject data=new JSONObject();
        data.put("region","nrt");
        data.put("plan","vhf-1c-1gb");
        data.put("snapshot_id",snapshot_id);
        HttpResponse httpResponse=HttpRequest.post("https://api.vultr.com/v2/instances")
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer "+TOKEN)
                .body(data.toString()).execute();
        String content=httpResponse.body();
        JSONObject temp_server=JSON.parseObject(content);
        String instance=null;
        try {
            instance=temp_server.get("instance").toString();
        }catch (Exception e){
            result.put("msg","snapshot_id 无效，等待快照制作完成再进行尝试。");
            result.put("code",401);
            return result;
        }

        temp_server=JSON.parseObject(instance);
        Server target_server = getServer(temp_server);

        if(httpResponse.getStatus()==200 || httpResponse.getStatus()==202){
            result.put("msg","添加服务器成功");
            result.put("data",target_server);
        }else{
            result.put("msg","添加服务器失败");
            result.put("data",content);
        }
        result.put("code",httpResponse.getStatus());
        this.getServers();

        return result;
    }

    @RequestMapping("/getSnapshots")
    @ResponseBody
    public String getSnapshots(){
        HttpResponse httpResponse=HttpRequest.get("https://api.vultr.com/v2/snapshots")
                .header("Authorization","Bearer "+TOKEN)
                .execute();

        String content=httpResponse.body();
        String snapshots=JSON.parseObject(content).get("snapshots").toString();
        String snap=JSON.parseArray(snapshots).get(0).toString();
        String id=JSON.parseObject(snap).get("id").toString();
        logger.info("id:{}",id);
        return id;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "force",defaultValue = "false")String force,@RequestParam(value = "id",defaultValue = "1234")String id){
        this.getServers();
        JSONObject result=new JSONObject();
        if("false".equals(force)){
            if(checkStatus()){
                result.put("msg","服务器操作中....，请稍后再试");
                result.put("code",401);
                return result;
            }
            if(servers.size()==1){
                result.put("msg","服务器数量为1，不能删除");
                result.put("code",401);
                return result;
            }
        }
        Server lastServer=null;
        try {
            lastServer=servers.get(0);
        }catch (IndexOutOfBoundsException e){
            result.put("msg","服务器数量小于1，不能删除；请先添加一台服务器");
            result.put("code",401);
            return result;
        }
        if(id.equals("1234")){
            id=lastServer.getId();
        }else {
            lastServer.setId(id);
        }
        if (servers.size()>0){
            String url=String.format("https://api.vultr.com/v2/instances/%s",id);
            logger.info("准备删除:{}",url);
            HttpResponse httpResponse=HttpRequest.delete(url)
                    .header("Authorization","Bearer "+TOKEN)
                    .execute();
            int code=httpResponse.getStatus();
            if(code==204){
                result.put("msg","删除成功:"+id);
                logger.warn("成功删除:{}",id);
            }else{
                result.put("msg","删除失败，请稍后重试。");
            }
            result.put("code",code);
        }else{
            result.put("msg","服务器数量小于1，不能删除；请先添加一台服务器");
            result.put("code",401);
        }
        return result;
    }

    @Async
    public void getServers(){
        HttpResponse httpResponse=HttpRequest.get(Constants.BASE_URL+"instances")
                .header("Authorization","Bearer "+TOKEN)
                .execute();
        String content=httpResponse.body();

        JSONObject serverInfo= JSON.parseObject(content);
        JSONArray instances=JSON.parseArray(serverInfo.get("instances").toString());
        servers.clear();
        for (int i = 0; i < instances.size(); i++) {
            JSONObject temp_server=JSON.parseObject(instances.get(i).toString());
            Server target_server = getServer(temp_server);
            servers.add(target_server);
            logger.info("检测到 server:{}",instances.get(i));
        }
    }

    private Server getServer(JSONObject temp_server) {
        String ip = temp_server.get("main_ip").toString();
        String id = temp_server.get("id").toString();
        String status = temp_server.get("power_status").toString();
        return new Server(id, ip, "", status);
    }

    private boolean checkStatus(){
        for (int i = 0; i < servers.size(); i++) {
            if (!servers.get(i).getStatus().equals("running") || servers.get(i).getMain_ip().equals("0.0.0.0")){
                return true;
            }
        }
        return false;
    }
}
