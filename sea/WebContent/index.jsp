<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html public "-/w3c/dtd xhtml 1.1/en"
"http://www.w3.org/tr/xhtml11/dtd/xhtml11.dtd">
<%
	if (request.getSession().getAttribute("pSysUser") == null)
		response.sendRedirect("login.jsp");
%>
<html>

<head>
<script type="text/javascript">
LOGGEDUSER='${sessionScope.pSysUser.name }';
LOGGEDORGNO='${sessionScope.pSysUser.orgNo }';
window.onunload = onunload_handler;
function onunload_handler(){
	//页面关闭时 清除session，刷新时不清除
//	if(window.event.clientX<=0 && window.event.clientY<0) { 
		Ext.Ajax.request({
			url : './sysman/securityman/logoutAction!logout.action'
		});
//	}
}  
</script>

<title>电力用户用电信息采集系统</title>
<meta http-equiv="X-UA-Compatible" content="chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" id="theme" type="text/css"
	href="./ext3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./js/vtab/ext-patch.css" />
<script type="text/javascript" src="./ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="./ext3/ext-all.js"></script>
<script type="text/javascript" src="./ext3/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="./js/public/ColumnHeaderGroup.js"></script>
<script type="text/javascript" src="./js/vtab/TabPanel.js"></script>
<script type="text/javascript" src="./js/public.js"></script>
<script type="text/javascript" src="index.js"></script>
<script type="text/javascript" src="./baseApp/dataGatherMan/loadPage.js"></script>
<style type="text/css">
img {
	azimuth: expression(this.pngSet ?                               this.pngSet =
		     
		                
		       true :(          
		       
		            this.nodeName ==              
				              "IMG" &&                              
		  this.src.toLowerCase (   
		) .    
		  
		           
		           indexOf('.png') >            
		    
		             -1 ?(              

		              this.runtimeStyle.backgroundImage =  
		                        
		     "none", this.runtimeStyle.filter =              
		            
		 "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" +    
		
		  
		     
		              this.src +                            
		    "', sizingMethod='image')", this.src =         
		    
		              "./ext3/resources/images/default/s.gif" ) :(    
		                
		         this.origBg =           
		  
		              this.origBg ?                                
		this.origBg : 
		  
		         
		              this.currentStyle.backgroundImage.toString (     
		                  
		      ) .        
		     
		              replace('url("', '') .                          
		    replace('")', ''), this.runtimeStyle.filter =              

		            
		 "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" +    
		
		  
		     
		              this.origBg +                            
		    "', sizingMethod='crop')", this.runtimeStyle.backgroundImage =    
		                            "none" ) ), this.pngSet =            
		 
		              true );
}

.settings {
	background-image:
		url(./ext3/examples/shared/icons/fam/folder_wrench.png);
}

.nav {
	background-image: url(./ext3/examples/shared/icons/fam/folder_go.png);
}

/* logo 折叠图标 */
.top_collapse {
	background-image: url(./images/up2.gif) !important;
}

.top_expand {
	background-image: url(./images/down2.gif) !important;
}

/*工具栏图标*/
.exportExcel {
	background-image: url(./images/exportExcel.png) !important;
}

.PublicUser {
	background-image: url(./images/PublicUser.png) !important;
}

.SpecialUser {
	background-image: url(./images/SpecialUser.png) !important;
}
.TransFormUser {
	background-image: url(./images/TransFormUser.gif) !important;
}
.abc {
background:transparent url('./js/public/spinner.gif') no-repeat 0 0 !important;
}

/*菜单图标*/
.accountBalance {
	background-image: url(./images/accountBalance.png) !important;
}

.ammeterRunStatus {
	background-image: url(./images/ammeterRunStatus.png) !important;
}

.archivesGet {
	background-image: url(./images/archivesGet.png) !important;
}

.archivesMaintain {
	background-image: url(./images/archivesMaintain.png) !important;
}

.archivesManage {
	background-image: url(./images/archivesManage.gif) !important;
}

.areaPowerDist {
	background-image: url(./images/areaPowerDist.gif) !important;
}

.authorize {
	background-image: url(./images/authorize.png) !important;
}

.autoSendQuery {
	background-image: url(./images/autoSendQuery.png) !important;
}

.autoSend {
	background-image: url(./images/autoSend.png) !important;
}

.BatchFetch {
	background-image: url(./images/BatchFetch.png) !important;
}

.channelMonitor {
	background-image: url(./images/channelMonitor.png) !important;
}

.codeMan {
	background-image: url(./images/codeMan.png) !important;
}

.consumerInfo {
	background-image: url(./images/consumerInfo.png ) !important;
}

.currLoadMonitor {
	background-image: url(./images/currLoadMonitor.gif) !important;
}

.dataAbnormal {
	background-image: url(./images/dataAbnormal.png) !important;
}

.DataFetch {
	background-image: url(./images/DataFetch.png) !important;
}

.DataGatherMan {
	background-image: url(./images/DataGatherMan.png) !important;
}

.dataPublish {
	background-image: url(./images/dataPublish.png) !important;
}

.deviceAbnormal {
	background-image: url(./images/deviceAbnormal.png) !important;
}

.DeviceMonitor {
	background-image: url(./images/DeviceMonitor.png) !important;
}

.dunningControl {
	background-image: url(./images/dunningControl.png) !important;
}

.EffectEvaluate {
	background-image: url(./images/EffectEvaluate.png) !important;
}

.EnergyControl {
	background-image: url(./images/EnergyControl.png) !important;
}

.eventManage {
	background-image: url(./images/eventManage.png) !important;
}

.GatherQualityEvaluate {
	background-image: url(./images/GatherQualityEvaluate.png) !important;
}

.GatherTaskCompile {
	background-image: url(./images/GatherTaskCompile.png) !important;
}

.gathor_set {
	background-image: url(./images/gathor_set.png) !important;
}

.gatherByHand {
	background-image: url(./images/gatherByHand.png) !important;
}

.generalAnalyse {
	background-image: url(./images/generalAnalyse.gif) !important;
}

.gisview {
	background-image: url(./images/gisview.png) !important;
}

.GroupSet {
	background-image: url(./images/GroupSet.png) !important;
}

.icoadv {
	background-image: url(./images/icoadv.png) !important;
}

.icobase {
	background-image: url(./images/icobase.png) !important;
}

.icoqry {
	background-image: url(./images/icoqry.png) !important;
}

.icorun {
	background-image: url(./images/icorun.png) !important;
}

.icosys {
	background-image: url(./images/icosys.png) !important;
}

.illegalFrameQry {
	background-image: url(./images/illegalFrameQry.gif) !important;
}

.kickoff {
	background-image: url(./images/kickoff.png) !important;
}

.lineLoseAnalyze {
	background-image: url(./images/lineLoseAnalyze.png) !important;
}

.logout {
	background-image: url(./images/logout.png) !important;
}

.logEdit {
	background-image: url(./images/logEdit.png) !important;
}
.logQuery {
	background-image: url(./images/logQuery.png) !important;
}
.loguser {
	background-image: url(./images/loguser.png) !important;
}

.losePowerStat {
	background-image: url(./images/losePowerStat.gif) !important;
}

.mainSationStatus {
	background-image: url(./images/mainSationStatus.png) !important;
}

.modelManage {
	background-image: url(./images/modelManage.png) !important;
}

.money {
	background-image: url(./images/money.png) !important;
}

.operatorMan {
	background-image: url(./images/operatorMan.png) !important;
}

.origFrameQry {
	background-image: url(./images/origFrameQry.png) !important;
}

.passMan {
	background-image: url(./images/passMan.png) !important;
}

.PlanCompile {
	background-image: url(./images/PlanCompile.png) !important;
}

.PlanExcute {
	background-image: url(./images/PlanExcute.png) !important;
}

.PlanPowerConsume {
	background-image: url(./images/PlanPowerConsume.png) !important;
}

.PowerControl {
	background-image: url(./images/PowerControl.png) !important;
}

.powerSortAnalyse {
	background-image: url(./images/powerSortAnalyse.gif) !important;
}

.prePaidControl {
	background-image: url(./images/prePaidControl.png) !important;
}

.prePaidDebug {
	background-image: url(./images/prePaidDebug.png) !important;
}

.prePaidParaSet {
	background-image: url(./images/prePaidParaSet.png) !important;
}

.prePaidStatus {
	background-image: url(./images/prePaidStatus.png) !important;
}

.prePaidStat {
	background-image: url(./images/prePaidStat.png) !important;
}

.privateTerminal {
	background-image: url(./images/privateTerminal.gif) !important;
}

.priviligeManage {
	background-image: url(./images/priviligeManage.png) !important;
}

.protect {
	background-image: url(./images/protect.png) !important;
}

.publicTerminal {
	background-image: url(./images/publicTerminal.gif) !important;
}

.RemoteControl {
	background-image: url(./images/RemoteControl.png) !important;
}

.roleMan {
	background-image: url(./images/roleMan.png) !important;
}

.SchemeDetermine {
	background-image: url(./images/SchemeDetermine.png) !important;
}

.SchemeExamine {
	background-image: url(./images/SchemeExamine.png) !important;
}

.sendDataQuery {
	background-image: url(./images/sendDataQuery.gif) !important;
}

.sIMInstallStat {
	background-image: url(./images/sIMInstallStat.png) !important;
}

.sIMOverTraffic {
	background-image: url(./images/sIMOverTraffic.png) !important;
}

.sIMTraffic {
	background-image: url(./images/sIMTraffic.png) !important;
}

.Survey {
	background-image: url(./images/Survey.png) !important;
}

.SuspensionControl {
	background-image: url(./images/SuspensionControl.png) !important;
}

.systemAbnormal {
	background-image: url(./images/systemAbnormal.png) !important;
}

.taskList {
	background-image: url(./images/taskList.png) !important;
}

.taskModel {
	background-image: url(./images/taskModel.png) !important;
}

.taskProcess {
	background-image: url(./images/taskProcess.png) !important;
}

.taskStat {
	background-image: url(./images/taskStat.png) !important;
}

.terminalChange {
	background-image: url(./images/terminalChange.gif) !important;
}

.terminalCoverage {
	background-image: url(./images/terminalCoverage.gif) !important;
}

.terminalDebug {
	background-image: url(./images/terminalDebug.png) !important;
}

.terminalFix {
	background-image: url(./images/terminalFix.png) !important;
}

.terminalInspect {
	background-image: url(./images/terminalInspect.gif) !important;
}

.terminalInstall {
	background-image: url(./images/terminalInstall.gif) !important;
}

.tmnlInstallDetail {
	background-image: url(./images/terminalDebug.png) !important;
}

.terminalOverhaul {
	background-image: url(./images/terminalOverhaul.gif) !important;
}

.terminalParaSet {
	background-image: url(./images/terminalParaSet.gif) !important;
}

.terminalRemove {
	background-image: url(./images/terminalRemove.gif) !important;
}

.terminalRunStatus {
	background-image: url(./images/terminalRunStatus.png) !important;
}

.terminalStat {
	background-image: url(./images/terminalStat.png) !important;
}

.testList {
	background-image: url(./images/testList.png) !important;
}

.tradeTrendline {
	background-image: url(./images/tradeTrendline.gif) !important;
}

.watchAnalyze {
	background-image: url(./images/watchAnalyze.png) !important;
}

.mainpage {
	background-image: url(./images/mainPage.png) !important;
}

.favor {
	background-image: url(./images/favor.png) !important;
}

/*按钮和报警图标*/
.advance {
	background-image: url(./images/advance.gif) !important;
}

.information {
	background-image: url(./images/information.gif) !important;
}

.query {
	background-image: url(./images/query.gif) !important;
}

.serious {
	background-image: url(./images/serious.gif) !important;
}

.subordination {
	background-image: url(./images/subordination.gif) !important;
}

/*左树图标*/
.net-02 {
	background-image: url(./images/net-02.png) !important;
}

.net-03 {
	background-image: url(./images/net-03.png) !important;
}

.net-04 {
	background-image: url(./images/net-04.png) !important;
}

.net-06 {
	background-image: url(./images/net-06.png) !important;
}

.net-subs {
	background-image: url(./images/net-subs.png) !important;
}

.net-line {
	background-image: url(./images/net-line.png) !important;
}

.net-terminal {
	background-image: url(./images/net-terminal.png) !important;
}

.cotrol-group {
	background-image: url(./images/control-group.png) !important;
}

.common-group {
	background-image: url(./images/common-group.png) !important;
}

/*按钮图标*/
.plus {
	background-image: url(./images/plus16.png) !important;
}

.minus {
	background-image: url(./images/minus16.png) !important;
}

.cancel {
	background-image: url(./images/cancel16.png) !important;
}

.next {
	background-image: url(./images/next0.gif) !important;
}

.previous {
	background-image: url(./images/prev0.gif) !important;
}
</style>
</head>
<body>
<div id="logo"><!--  -->
<center>
<table 　width="100%" height="64">
	<tr>
		<td></td>
		<td width="920" background="./images/logo.png" valign="bottom"
			align="right" valign="bottom"><!--<span
			style="font-size: 12px; color: #ffffff">登录用户:
		${sessionScope.pSysUser.name } &nbsp;&nbsp;&nbsp;
		${sessionScope.userarea } <a href='logout.jsp'>退出</a></span> --></td>
		<td></td>
	</tr>
</table>

</center>
</div>


</body>
</html>
