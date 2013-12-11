/*
 * icyfire
 * 
 * http://icyfire.javaeye.com
 */
Ext.override(Ext.TabPanel, {
    initComponent : function(){
        if (this.tabPosition == 'left' || this.tabPosition == 'right') {
            this.cls = this.cls || '';
            this.cls = this.cls + ' x-tab-panel-vertical x-tab-panel-vertical-' + this.tabPosition;
            this.intendedTabPosition = this.tabPosition;
            this.verticalTabs = true;
            this.tabPosition = 'top';
            this.tabWidth = 30;
        }
        this.frame = false;
        Ext.TabPanel.superclass.initComponent.call(this);
        this.addEvents(
            
            'beforetabchange',
            
            'tabchange',
            
            'contextmenu'
        );
        this.setLayout(new Ext.layout.CardLayout({
            deferredRender: this.deferredRender
        }));
        if(this.tabPosition == 'top'){
            this.elements += ',header';
            this.stripTarget = 'header';
        }else {
            this.elements += ',footer';
            this.stripTarget = 'footer';
        }
        if(!this.stack){
            this.stack = Ext.TabPanel.AccessStack();
        }
        this.initItems();
    },
    
    afterRender : function() {
        Ext.TabPanel.superclass.afterRender.call(this);
        if(this.autoTabs){
            this.readTabs(false);
        }
        if (this.verticalTabs) {
            this.header.setWidth(30);
            this.header.setHeight(this.height || this.container.getHeight());
        }
    },
  
    adjustBodyHeight : function(h) {
        if (this.verticalTabs) {
            this.header.setHeight(h + (this.tbar ? this.tbar.getHeight() : 0));
        }
        return Ext.TabPanel.superclass.adjustBodyHeight.call(this, h);
    },
  
    getFrameWidth : function() {
        return Ext.TabPanel.superclass.getFrameWidth.call(this) + this.verticalTabs ? 30 : 0;
    },
  
    getFrameHeight : function() {
        return Ext.TabPanel.superclass.getFrameHeight.call(this) - (this.verticalTabs ? this.header.getHeight() : 0);
    },

    // private
    adjustBodyWidth : function(w){
        if (this.verticalTabs) {
            if (Ext.isIE6) {
                this.bwrap.setWidth(w - 3);
            }
            return w;
        }
        if(this.header){
            this.header.setWidth(w);
        }
        if(this.footer){
            this.footer.setWidth(w);
        }
        return w;
    },
    
    autoSizeTabs : function(){
        var count = this.items.length;
        var ce = this.tabPosition != 'bottom' ? 'header' : 'footer';
        var ow = this[ce].dom[this.verticalTabs ? 'offsetHeight' : 'offsetWidth'];
        var aw = this[ce].dom[this.verticalTabs ? 'clientHeight' : 'clientWidth'];

        if(!this.resizeTabs || count < 1 || !aw){ // !aw for display:none
            return;
        }
        
        var each = Math.max(Math.min(Math.floor((aw-4) / count) - this.tabMargin, this.tabWidth), this.minTabWidth); // -4 for float errors in IE
        this.lastTabWidth = each;
        var lis = this.stripWrap.dom.getElementsByTagName('li');
        for(var i = 0, len = lis.length-1; i < len; i++) { // -1 for the "edge" li
            var li = lis[i];
            var inner = li.childNodes[1].firstChild.firstChild;
            var tw = li[this.verticalTabs ? 'offsetHeight' : 'offsetWidth'];
            var iw = inner[this.verticalTabs ? 'offsetHeight' : 'offsetWidth'];
            inner.style[this.verticalTabs ? 'height' : 'width'] = (each - (tw-iw)) + 'px';
        }
    },

    // private
    autoScrollTabs : function(){
        var topTab = this.tabPosition == 'top';
        var count = this.items.length;
        var ow = this[topTab ? 'header' : 'footer'].dom[this.verticalTabs ? 'offsetHeight' : 'offsetWidth'];
        var tw = this[topTab ? 'header' : 'footer'].dom[this.verticalTabs ? 'clientHeight' : 'clientWidth'];

        var wrap = this.stripWrap;
        var cw = wrap.dom[this.verticalTabs ? 'offsetHeight' : 'offsetWidth'];
        var pos = this.getScrollPos();
        var l = this.edge.getOffsetsTo(this.stripWrap)[this.verticalTabs ? 1 : 0] + pos;

        if(!this.enableTabScroll || count < 1 || cw < 20){ // 20 to prevent display:none issues
            return;
        }
        if(l <= tw){
            wrap.dom.scrollLeft = 0;
            wrap[this.verticalTabs ? 'setWidth' : 'setHeight'](tw);
            if(this.scrolling){
                this.scrolling = false;
                this[topTab ? 'header' : 'footer'].removeClass('x-tab-scrolling');
                this.scrollLeft.hide();
                this.scrollRight.hide();
            }
        }else{
            if(!this.scrolling){
                this[topTab ? 'header' : 'footer'].addClass('x-tab-scrolling');
            }
            tw -= wrap.getMargins(this.verticalTabs ? 'tb' : 'lr');
            wrap[this.verticalTabs ? 'setHeight' : 'setWidth'](tw > 20 ? tw : 20);
            if(!this.scrolling){
                if(!this.scrollLeft){
                    this.createScrollers();
                }else{
                    this.scrollLeft.show();
                    this.scrollRight.show();
                }
            }
            this.scrolling = true;
            if(pos > (l-tw)){ // ensure it stays within bounds
                wrap.dom.scrollLeft = l-tw;
            }else{ // otherwise, make sure the active tab is still visible
                this.scrollToTab(this.activeTab, false);
            }
            this.updateScrollButtons();
        }
    },

    // private
    createScrollers : function(){
        var topTab = this.tabPosition == 'top';
        var h = this.stripWrap.dom[this.verticalTabs ? 'offsetWidth' : 'offsetHeight'];

        // left
        var sl = this[topTab ? 'header' : 'footer'].insertFirst({
                cls:'x-tab-scroller-left'
            });
        sl[this.verticalTabs ? 'setWidth' : 'setHeight'](h);
        if(this.verticalTabs) {
            sl.setHeight(this.stripWrap.getMargins('t'));
        }
        sl.addClassOnOver('x-tab-scroller-left-over');
        this.leftRepeater = new Ext.util.ClickRepeater(sl, {
            interval : this.scrollRepeatInterval,
            handler: this.onScrollLeft,
            scope: this
        });
        this.scrollLeft = sl;

        // right
        var sr = this[topTab ? 'header' : 'footer'].insertFirst({
                cls:'x-tab-scroller-right'
            });
        sr[this.verticalTabs ? 'setWidth' : 'setHeight'](h);
        if(this.verticalTabs) {
            sr.setHeight(this.stripWrap.getMargins('b'));
        }
        sr.addClassOnOver('x-tab-scroller-right-over');
        this.rightRepeater = new Ext.util.ClickRepeater(sr, {
            interval : this.scrollRepeatInterval,
            handler: this.onScrollRight,
            scope: this
        });
        this.scrollRight = sr;
    },

    // private
    getScrollWidth : function(){
        return this.edge.getOffsetsTo(this.stripWrap)[this.verticalTabs ? 1 : 0] + this.getScrollPos();
    },

    // private
    getScrollPos : function(){
        return parseInt(this.stripWrap.dom[this.verticalTabs ? 'scrollTop' : 'scrollLeft'], 10) || 0;
    },

    // private
    getScrollArea : function(){
        return parseInt(this.stripWrap.dom[this.verticalTabs ? 'clientHeight' : 'clientWidth'], 10) || 0;
    },

    

    scrollToTab : function(item, animate){
        if(!item){ return; }
        var el = this.getTabEl(item);
        var pos = this.getScrollPos(), area = this.getScrollArea();
        var left = Ext.fly(el).getOffsetsTo(this.stripWrap)[this.verticalTabs ? 1 : 0] + pos;
        var right = left + el[this.verticalTabs ? 'offsetHeight' : 'offsetWidth'];
        if(left < pos){
            this.scrollTo(left, animate);
        }else if(right > (pos + area)){
            this.scrollTo(right - area, animate);
        }
    },

    // private
    scrollTo : function(pos, animate){
        this.stripWrap.scrollTo(this.verticalTabs ? 'top' : 'left', pos, animate ? this.getScrollAnim() : false);
        if(!animate){
            this.updateScrollButtons();
        }
    }
});