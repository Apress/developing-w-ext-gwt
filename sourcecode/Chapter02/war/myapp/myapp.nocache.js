function myapp(){var l='',F='" for "gwt:onLoadErrorFn"',D='" for "gwt:onPropertyErrorFn"',n='"><\/script>',p='#',r='/',ub='<script defer="defer">myapp.onInjectionDone(\'myapp\')<\/script>',zb='<script id="',A='=',q='?',C='Bad handler "',tb='DOMContentLoaded',sb="GWT module 'myapp' needs to be (re)compiled, please run a compile or use the Compile/Browse button in hosted mode",o='SCRIPT',yb='__gwt_marker_myapp',s='base',nb='begin',cb='bootstrap',u='clear.cache.gif',z='content',xb='end',lb='gecko',mb='gecko1_8',vb='gwt.hybrid',E='gwt:onLoadErrorFn',B='gwt:onPropertyErrorFn',y='gwt:property',qb='hosted.html?myapp',kb='ie6',ab='iframe',t='img',bb="javascript:''",pb='loadExternalRefs',v='meta',eb='moduleRequested',wb='moduleStartup',jb='msie',m='myapp',w='name',gb='opera',db='position:absolute;width:0;height:0;border:none',ib='safari',rb='selectingPermutation',x='startup',ob='unknown',fb='user.agent',hb='webkit';var Bb=window,k=document,Ab=Bb.__gwtStatsEvent?function(a){return Bb.__gwtStatsEvent(a)}:null,pc,fc,ac,Fb=l,ic={},sc=[],oc=[],Eb=[],lc,nc;Ab&&Ab({moduleName:m,subSystem:x,evtGroup:cb,millis:(new Date()).getTime(),type:nb});if(!Bb.__gwt_stylesLoaded){Bb.__gwt_stylesLoaded={}}if(!Bb.__gwt_scriptsLoaded){Bb.__gwt_scriptsLoaded={}}function ec(){var b=false;try{b=Bb.external&&(Bb.external.gwtOnLoad&&Bb.location.search.indexOf(vb)==-1)}catch(a){}ec=function(){return b};return b}
function hc(){if(pc&&fc){var c=k.getElementById(m);var b=c.contentWindow;if(ec()){b.__gwt_getProperty=function(a){return bc(a)}}myapp=null;b.gwtOnLoad(lc,m,Fb);Ab&&Ab({moduleName:m,subSystem:x,evtGroup:wb,millis:(new Date()).getTime(),type:xb})}}
function cc(){var j,h=yb,i;k.write(zb+h+n);i=k.getElementById(h);j=i&&i.previousSibling;while(j&&j.tagName!=o){j=j.previousSibling}function f(b){var a=b.lastIndexOf(p);if(a==-1){a=b.length}var c=b.indexOf(q);if(c==-1){c=b.length}var d=b.lastIndexOf(r,Math.min(c,a));return d>=0?b.substring(0,d+1):l}
;if(j&&j.src){Fb=f(j.src)}if(Fb==l){var e=k.getElementsByTagName(s);if(e.length>0){Fb=e[e.length-1].href}else{Fb=f(k.location.href)}}else if(Fb.match(/^\w+:\/\//)){}else{var g=k.createElement(t);g.src=Fb+u;Fb=f(g.src)}if(i){i.parentNode.removeChild(i)}}
function mc(){var f=document.getElementsByTagName(v);for(var d=0,g=f.length;d<g;++d){var e=f[d],h=e.getAttribute(w),b;if(h){if(h==y){b=e.getAttribute(z);if(b){var i,c=b.indexOf(A);if(c>=0){h=b.substring(0,c);i=b.substring(c+1)}else{h=b;i=l}ic[h]=i}}else if(h==B){b=e.getAttribute(z);if(b){try{nc=eval(b)}catch(a){alert(C+b+D)}}}else if(h==E){b=e.getAttribute(z);if(b){try{lc=eval(b)}catch(a){alert(C+b+F)}}}}}}
function bc(d){var e=oc[d](),b=sc[d];if(e in b){return e}var a=[];for(var c in b){a[b[c]]=c}if(nc){nc(d,a,e)}throw null}
var dc;function gc(){if(!dc){dc=true;var a=k.createElement(ab);a.src=bb;a.id=m;a.style.cssText=db;a.tabIndex=-1;k.body.appendChild(a);Ab&&Ab({moduleName:m,subSystem:x,evtGroup:wb,millis:(new Date()).getTime(),type:eb});a.contentWindow.location.replace(Fb+qc)}}
oc[fb]=function(){var d=navigator.userAgent.toLowerCase();var b=function(a){return parseInt(a[1])*1000+parseInt(a[2])};if(d.indexOf(gb)!=-1){return gb}else if(d.indexOf(hb)!=-1){return ib}else if(d.indexOf(jb)!=-1){var c=/msie ([0-9]+)\.([0-9]+)/.exec(d);if(c&&c.length==3){if(b(c)>=6000){return kb}}}else if(d.indexOf(lb)!=-1){var c=/rv:([0-9]+)\.([0-9]+)/.exec(d);if(c&&c.length==3){if(b(c)>=1008)return mb}return lb}return ob};sc[fb]={gecko:0,gecko1_8:1,ie6:2,opera:3,safari:4};myapp.onScriptLoad=function(){if(dc){fc=true;hc()}};myapp.onInjectionDone=function(){pc=true;Ab&&Ab({moduleName:m,subSystem:x,evtGroup:pb,millis:(new Date()).getTime(),type:xb});hc()};cc();var qc;if(ec()){if(Bb.external.initModule&&Bb.external.initModule(m)){Bb.location.reload();return}qc=qb}mc();Ab&&Ab({moduleName:m,subSystem:x,evtGroup:cb,millis:(new Date()).getTime(),type:rb});if(!qc){try{alert(sb);return}catch(a){return}}var kc;function jc(){if(!ac){ac=true;hc();if(k.removeEventListener){k.removeEventListener(tb,jc,false)}if(kc){clearInterval(kc)}}}
if(k.addEventListener){k.addEventListener(tb,function(){gc();jc()},false)}var kc=setInterval(function(){if(/loaded|complete/.test(k.readyState)){gc();jc()}},50);Ab&&Ab({moduleName:m,subSystem:x,evtGroup:cb,millis:(new Date()).getTime(),type:xb});Ab&&Ab({moduleName:m,subSystem:x,evtGroup:pb,millis:(new Date()).getTime(),type:nb});k.write(ub)}
myapp();