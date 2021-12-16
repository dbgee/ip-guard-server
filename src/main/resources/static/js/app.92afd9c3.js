(function(e){function t(t){for(var n,s,i=t[0],c=t[1],u=t[2],d=0,f=[];d<i.length;d++)s=i[d],Object.prototype.hasOwnProperty.call(a,s)&&a[s]&&f.push(a[s][0]),a[s]=0;for(n in c)Object.prototype.hasOwnProperty.call(c,n)&&(e[n]=c[n]);l&&l(t);while(f.length)f.shift()();return o.push.apply(o,u||[]),r()}function r(){for(var e,t=0;t<o.length;t++){for(var r=o[t],n=!0,i=1;i<r.length;i++){var c=r[i];0!==a[c]&&(n=!1)}n&&(o.splice(t--,1),e=s(s.s=r[0]))}return e}var n={},a={app:0},o=[];function s(t){if(n[t])return n[t].exports;var r=n[t]={i:t,l:!1,exports:{}};return e[t].call(r.exports,r,r.exports,s),r.l=!0,r.exports}s.m=e,s.c=n,s.d=function(e,t,r){s.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:r})},s.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},s.t=function(e,t){if(1&t&&(e=s(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var r=Object.create(null);if(s.r(r),Object.defineProperty(r,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var n in e)s.d(r,n,function(t){return e[t]}.bind(null,n));return r},s.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return s.d(t,"a",t),t},s.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},s.p="/";var i=window["webpackJsonp"]=window["webpackJsonp"]||[],c=i.push.bind(i);i.push=t,i=i.slice();for(var u=0;u<i.length;u++)t(i[u]);var l=c;o.push([0,"chunk-vendors"]),r()})({0:function(e,t,r){e.exports=r("56d7")},"034f":function(e,t,r){"use strict";r("85ec")},"0d1f":function(e,t,r){},"56d7":function(e,t,r){"use strict";r.r(t);r("e260"),r("e6cf"),r("cca6"),r("a79d");var n=r("2b0e"),a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("img",{staticStyle:{"margin-bottom":"15px"},attrs:{alt:"Vue logo",src:r("cf05"),width:"200px"}}),n("Server")],1)},o=[],s=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{attrs:{id:"main"}},[r("div",{attrs:{id:"operation"}},[r("el-tag",[e._v("服务器操作")]),r("el-button",{attrs:{size:"small",type:"primary"},on:{click:e.getIP}},[e._v("获取服务器")]),r("el-button",{attrs:{size:"small",type:"warning"},on:{click:e.addServer}},[e._v("新增服务器")]),r("el-button",{attrs:{size:"small",type:"danger"},on:{click:e.deleteServer}},[e._v("删除旧服务器")]),r("el-button",{attrs:{size:"small",type:"info"},on:{click:function(t){e.showlog=!e.showlog}}},[e._v("开启日志")])],1),r("div",{attrs:{id:"output"}},[r("ol",e._l(e.server_info,(function(t){return r("li",{key:t.id},[r("div",[e._v(" "+e._s(t.main_ip)+"--\x3e"+e._s(t.status)+" "),r("el-button",{attrs:{size:"small"},on:{click:function(r){return e.forceDelete(t.id)}}},[e._v("强制删除")])],1)])})),0),r("div",{attrs:{id:"log"}},[r("hr",{directives:[{name:"show",rawName:"v-show",value:e.showlog,expression:"showlog"}]}),r("hr",{directives:[{name:"show",rawName:"v-show",value:e.showlog,expression:"showlog"}]}),r("p",{directives:[{name:"show",rawName:"v-show",value:e.showlog,expression:"showlog"}]},[e._v(e._s(e.server_data))])])])])},i=[],c={name:"Server",data:function(){return{showlog:!1,server_data:"",serverinfo:[]}},computed:{server_info:function(){return this.serverinfo}},mounted:function(){this.fetchServers()},methods:{fetchServers:function(){var e=this;this.axios.get("/list").then((function(t){e.server_data=t.data,e.serverinfo=t.data.servers,401!==t.data.code?e.server_data=t.data:e.$message.warning(t.data.msg)})).catch((function(t){e.$message.error(t)}))},getIP:function(){this.fetchServers(),this.$message.success("获取服务器...")},addServer:function(){var e=this;this.axios.get("/add").then((function(t){e.server_data=t.data,200===t.data.code&&202===t.data.code?(e.$message.success("新增服务器中 "+t.status),e.fetchServers()):e.$message.warning(t.data.msg)})).catch((function(t){e.$message.error(t)}))},deleteServer:function(){var e=this;this.axios.get("/delete?force=false").then((function(t){e.server_data=t.data,204===t.data.code?e.$message.success("删除服务器 中"+t.status):e.$message.error(t.data.msg)})).catch((function(t){e.$message.error(t)}))},forceDelete:function(e){var t=this;this.axios.get("/delete?force=true&id="+e).then((function(e){if(t.server_data=e.data,401===e.data.code||500===e.data.code||404===e.data.code)return console.log("code:"+e.data.code),void t.$message.error(e.data.msg);t.$message.success("强制删除服务器 中"+e.status)})).catch((function(e){t.$message.error(e)}))}}},u=c,l=(r("836d"),r("2877")),d=Object(l["a"])(u,s,i,!1,null,"905d016e",null),f=d.exports,v={name:"App",components:{Server:f}},p=v,h=(r("034f"),Object(l["a"])(p,a,o,!1,null,null,null)),g=h.exports,m=r("5c96"),w=r.n(m),_=(r("0fae"),r("bc3a")),b=r.n(_),y=r("130e");n["default"].config.productionTip=!1,n["default"].use(w.a),n["default"].use(y["a"],b.a),new n["default"]({render:function(e){return e(g)}}).$mount("#app")},"836d":function(e,t,r){"use strict";r("0d1f")},"85ec":function(e,t,r){},cf05:function(e,t,r){e.exports=r.p+"img/logo.da3f9412.png"}});
//# sourceMappingURL=app.92afd9c3.js.map