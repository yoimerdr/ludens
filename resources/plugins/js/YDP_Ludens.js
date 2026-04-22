///=============================================================================
/// YDP_Ludens | 1.1.0 | April 22nd 2026
///=============================================================================

/*:
 * @plugindesc
 * Bridge plugin between games and the Ludens mobile client. Required for certain features.
 *
 * @help
 * =============================================================================
 *
 * __   ______  ____    _              _
 * \ \ / /  _ \|  _ \  | |   _   _  __| | ___ _ __  ___
 *  \ V /| | | | |_) | | |  | | | |/ _` |/ _ \ '_ \/ __|
 *   | | | |_| |  __/  | |__| |_| | (_| |  __/ | | \__ \
 *   |_| |____/|_|     |_____\__,_|\__,_|\___|_| |_|___/
 * =============================================================================
 *
 * YDP_Ludens
 * =============================================================================
 *
 * This plugin serves as a bridge between your game and the Ludens client,
 * an project designed to run games on mobile devices. It is necessary for
 * enabling specific features in the mobile environment.
 *
*/

var YDP_Ludens=function(n){"use strict";var e=Array.isArray,t=void 0;function r(n,t){var r=typeof t;return(e(n)?n:[n]).every(function(n){return r===n})}function o(n){return n!==t&&null!==n}function i(n,e,t){return n.apply(e,arguments[2])}function a(n,e,t){return function(n,e,t,r){return i(n.prototype[e],t,arguments[3])}(Array,"slice",n,[e,t])}function u(n){return i(n.concat,n,a(arguments,1))}function s(n,e){return i(n.bind,n,u([e],a(arguments,2)))}var f=s(r,t,"function");var c,l=Object,d=l.getOwnPropertyNames,p=l.defineProperty,v=(c="prototype",function(n){return n[c]});function h(n,e){return n?n[e]:t}function m(n,e,r){return n?(n[e]=r,r):t}function g(n,e,t){p(n,e,{get:t,set:void 0,configurable:!1,enumerable:!0})}var y=Math.random,b=0,_=y();function w(n){return u("Symbol('",o(e=n)?e.toString():f(t)?t():"","')_",i(1..toString,++b+_,[36]));var e,t}var M=["bgmVolume","bgsVolume","meVolume","seVolume"],E=w("m"),F=w("v");function C(n){var e=this,t=AudioManager;n=(n|=0)<0?0:n>100?100:n,o(h(e,F))||m(e,F,{}),M.forEach(function(r){o(h(t,r))&&(m(h(e,F),r,h(t,r)),m(t,r,n))})}var P={mute:function(){i(C,this,[0]),m(this,E,!0)},unmute:function(){var n=h(this,F),e=AudioManager;M.forEach(function(t){o(h(e,t))&&o(h(n,t))&&m(e,t,h(n,t))}),m(this,E,!1)},volume:C,toggle:function(){var n=this;n.isMuted?n.unmute():n.mute()}};function A(n){var e=Graphics;!function(n,e){o(h(n,"_fpsMeter"))&&(m(n,"_fpsMeterToggled",!1),e&&f(n.showFps)?n.showFps():!e&&f(n.hideFps)&&n.hideFps())}(e,n),function(n,e){var t=h(n,"_fpsCounter");if(o(t)){var r=h(h(t,"_boxDiv"),"style");r&&f(t._update)&&(r.display=e?"block":"none",t._showFps=e,t._update())}}(e,n)}g(P,"isMuted",function(){var n=AudioManager;return M.every(function(e){return 0===h(n,e)})});var G={show:function(){A(!0)},hide:function(){A(!1)},toggle:function(){A(!this.isVisible)}};function L(n,e){e.forEach(function(e){return delete n[e]})}function V(n){L(n,d(n))}g(G,"isVisible",function(){var n=Graphics,e=h(n,"_fpsMeter"),t=h(n,"_fpsCounter");return o(e)?!e.isPaused:!!o(t)&&!!t._showFps});var N,O={on:function(n,e){f(e)&&(h(N,n)||m(N,n,[])).push(e)},off:function(n,e){var t=h(N,n);t&&t.splice(t.indexOf(e),1)},emit:function(n,e){var t=h(N,n),r=a(arguments,2);t&&t.forEach(function(n){return i(n,e,r)})},dispose:function(){L(N,a(arguments))},disposes:s(V,t,N={})};function j(n,e,t){if(!o(s=t)||!r("object",s))throw new Error("For extend a method is required an builder object.");var s;if(["replace","modifyParameters","beforeCall","afterCall"].some(Object.prototype.hasOwnProperty,t)){var c=n[e];if(!f(c))throw new Error("The target property value to extend must be a function.");var l=t.replace,d=t.modifyParameters,p=t.beforeCall,v=t.afterCall;n[e]=function(){var n=a(arguments),e=this;if(l)return i(l,e,u([c],n));d&&(n=i(d,e,n)),p&&i(p,e,n);var t=i(c,e,n);return v?i(v,e,u([t],n)):t}}}var S=document;return m(n,"events",{on:O.on,off:O.off}),function(){var n="_setupCssFontLoading",e="MV"==Utils.RPGMAKER_NAME;Graphics&&f(h(Graphics,n))&&j(Graphics,n,{replace:function(n){f(S.fonts.ready.then)?i(n,this):m(Graphics,"_cssFontLoading",void 0)}});var t=!1;j(v(Scene_Title),"start",{afterCall:function(){if(!t){t=!0,O.emit("onload");var n=h(window,"LudensBridge");n&&n.callNative&&n.callNative("LudensLoader",JSON.stringify({isEnabled:!0,isLoading:!1,canToggleDrawEngine:e}))}}}),j(Bitmap,"load",{modifyParameters:function(){var n=arguments;return!Utils.isNwjs()&&n[0]&&e&&(n[0]=encodeURIComponent(n[0]).replace(/%2F/g,"/")),n}})}(),n.audio=P,n.fps=G,n}({});