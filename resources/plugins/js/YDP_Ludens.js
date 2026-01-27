///=============================================================================
/// YDP_Ludens | 1.0.0 | January 27th 2026
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

var YDP_Ludens=function(n){"use strict";var t=Array.isArray,e=void 0;function r(n,e){var r=typeof e;return(t(n)?n:[n]).every(function(n){return r===n})}function o(n){return n!==e&&null!==n}function i(n,t,e){return n.apply(t,arguments[2])}function u(n,t,e){return function(n,t,e,r){return i(n.prototype[t],e,arguments[3])}(Array,"slice",n,[t,e])}function a(n){return i(n.concat,n,u(arguments,1))}function f(n,t){return i(n.bind,n,a([t],u(arguments,2)))}var s=f(r,e,"function");var c,d=Object,l=d.getOwnPropertyNames,p=d.defineProperty,v=(c="prototype",function(n){return n[c]});function h(n,t){return n?n[t]:e}function m(n,t,r){return n?(n[t]=r,r):e}function g(n,t,e){p(n,t,{get:e,set:void 0,configurable:!1,enumerable:!0})}var y=Math.random,b=0,_=y();function w(n){return a("Symbol('",o(t=n)?t.toString():s(e)?e():"","')_",i(1..toString,++b+_,[36]));var t,e}var F=["bgmVolume","bgsVolume","meVolume","seVolume"],M=w("m"),C=w("v");function E(n){var t=this,e=AudioManager;n=(n|=0)<0?0:n>100?100:n,o(h(t,C))||m(t,C,{}),F.forEach(function(r){o(h(e,r))&&(m(h(t,C),r,h(e,r)),m(e,r,n))})}var L={mute:function(){i(E,this,[0]),m(this,M,!0)},unmute:function(){var n=h(this,C),t=AudioManager;F.forEach(function(e){o(h(t,e))&&o(h(n,e))&&m(t,e,h(n,e))}),m(this,M,!1)},volume:E,toggle:function(){var n=this;n.isMuted?n.unmute():n.mute()}};function P(n){var t=Graphics;!function(n,t){o(h(n,"_fpsMeter"))&&(m(n,"_fpsMeterToggled",!1),t&&s(n.showFps)?n.showFps():!t&&s(n.hideFps)&&n.hideFps())}(t,n),function(n,t){var e=h(n,"_fpsCounter");if(o(e)){var r=h(h(e,"_boxDiv"),"style");r&&s(e._update)&&(r.display=t?"block":"none",e._showFps=t,e._update())}}(t,n)}g(L,"isMuted",function(){var n=AudioManager;return F.every(function(t){return 0===h(n,t)})});var A={show:function(){P(!0)},hide:function(){P(!1)},toggle:function(){P(!this.isVisible)}};function O(n,t){t.forEach(function(t){return delete n[t]})}function V(n){O(n,l(n))}g(A,"isVisible",function(){var n=Graphics,t=h(n,"_fpsMeter"),e=h(n,"_fpsCounter");return o(t)?!t.isPaused:!!o(e)&&!!e._showFps});var S,j={on:function(n,t){s(t)&&(h(S,n)||m(S,n,[])).push(t)},off:function(n,t){var e=h(S,n);e&&e.splice(e.indexOf(t),1)},emit:function(n,t){var e=h(S,n),r=u(arguments,2);e&&e.forEach(function(n){return i(n,t,r)})},dispose:function(){O(S,u(arguments))},disposes:f(V,e,S={})};function x(n,t,e){if(!o(f=e)||!r("object",f))throw new Error("For extend a method is required an builder object.");var f;if(["replace","modifyParameters","beforeCall","afterCall"].some(Object.prototype.hasOwnProperty,e)){var c=n[t];if(!s(c))throw new Error("The target property value to extend must be a function.");var d=e.replace,l=e.modifyParameters,p=e.beforeCall,v=e.afterCall;n[t]=function(){var n=u(arguments),t=this;if(d)return i(d,t,a([c],n));l&&(n=i(l,t,n)),p&&i(p,t,n);var e=i(c,t,n);return v?i(v,t,a([e],n)):e}}}var G=document;return m(n,"events",{on:j.on,off:j.off}),function(){x(Graphics,"_setupCssFontLoading",{replace:function(n){s(G.fonts.ready.then)?i(n,this):m(Graphics,"_cssFontLoading",void 0)}});var n=!1;x(v(Scene_Title),"start",{afterCall:function(){if(!n){n=!0,j.emit("onload");var t=h(window,"LudensBridge");t&&t.callNative&&t.callNative("LudensLoader",JSON.stringify({isEnabled:!0,isLoading:!1}))}}})}(),n.audio=L,n.fps=A,n}({});