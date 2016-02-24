<span style="font-size: 18pt;">推送是使用非常频繁的服务，使用方法参见bmob官方文档，<a href="http://docs.bmob.cn/androidpush/index.html?menukey=otherdoc&amp;key=androidpush" target="_blank">http://docs.bmob.cn/androidpush/index.html?menukey=otherdoc&amp;key=androidpush</a> 。</span>

<span style="font-size: 18pt;">但是没有整体管理功能，可拓展性有限，当信息量较大的时候，使用起来并不方便，本来旨在解决这一问题。</span>

&nbsp;

<span style="font-size: 18pt;"><strong>总体设计</strong></span>

&nbsp;

<span style="font-size: 18pt;"><a href="http://7xiot4.com1.z0.glb.clouddn.com/BmobPush.png"><img class="alignnone size-full wp-image-250" src="http://7xiot4.com1.z0.glb.clouddn.com/BmobPush.png" alt="BmobPush" width="326" height="541" /></a></span>

&nbsp;

<span style="font-size: 18pt;">整体分为发送者和接收者。</span>

<span style="font-size: 18pt;">发送者负责构建消息和推送消息。</span>

<span style="font-size: 18pt;"><strong>构建消息：</strong></span>

<span style="font-size: 18pt;">以构建java对象的方式，构建消息，不必直接构建json字符串消息。</span>

<span style="font-size: 18pt;"><strong>推送消息：</strong></span>

<span style="font-size: 18pt;">给BmobInstallation加一个targetUserObjectId字段，每次用户登录更新该字段。</span>

<span style="font-size: 18pt;">使用fastjson将java对象转换为json字符串，再推送给目标用户。</span>

&nbsp;

&nbsp;

<span style="font-size: 18pt;">接收者负责解析消息和管理消息。</span>

<span style="font-size: 18pt;"><strong>解析消息：</strong></span>

<span style="font-size: 18pt;">同样使用fastjson将json字符串转换为java对象。</span>

<span style="font-size: 18pt;"><strong>管理消息：</strong></span>

<span style="font-size: 18pt;">通过ormlite关系型数据库进行存储更新删除等，不必写sql语句。</span>

&nbsp;

<span style="font-size: 18pt;">更多详细内容请看源码。</span>

&nbsp;

<span style="font-size: 18pt;"><strong>源码说明：</strong></span>

<span style="font-size: 18pt;">1.开源，<a href="https://github.com/klob/BmobPushSample">https://github.com/klob/BmobPushSample</a></span>

<span style="font-size: 18pt;">2.使用了klob-sdk框架</span>

<span style="font-size: 18pt;">3.从成型项目抽出来，更多功能可自行探索</span>

&nbsp;

<span style="font-size: 18pt;"><strong>demo说明：</strong></span>

<span style="font-size: 18pt;">1.请先卸载使用了bmob sdk的app</span>

<span style="font-size: 18pt;">2.建议使用两台手机测试</span>

&nbsp;

<span style="font-size: 18pt;"><strong>使用说明：</strong></span>

<span style="font-size: 18pt; line-height: 36px;">1.导入bmobpush,并进行定制修改（推荐）</span>

<span style="font-size: 18pt;">2.gradle compile 'com.diandi.klob:bmobpush:1.0.0'</span>

&nbsp;

&nbsp;

&nbsp;

&nbsp;