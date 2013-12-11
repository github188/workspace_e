/*
 * Ext.onReady(function(){
 * 
 * var tb = new Ext.Toolbar({ renderTo : document.body, items:[{ xtype
 * :"tbbutton", text : "button", handler : function(f) { Ext.Msg.alert("bobo I
 * am here"); } }, { xtype : "tbbutton", cls : "x-btn-text-icon", icon :
 * "./images/gisview.png", text : "Menu Button", menu : [ { text : "better" },
 * {text : "good"}, {text : "best"} ] },{ xtype : "tbsplit", text : "Split
 * Button", menu : [ {text : "better"}, {text : "good"}, {text : "best"} ] } ]
 * 
 * }); });
 * 
 */
Ext.onReady(function() {
			var viewport = new Ext.Viewport({
						layout : 'border',
						id : 'movieView',
						renderTo : Ext.getBody(),
						items : [{
							region : "north",
							xtype : 'toolbar',
							height : 24,
							items : [{
										xtype : 'tbspacer'
									}, new Ext.Toolbar.Button({
										//xtype : 'tbbutton',
										id : 'tbbutton',
										text : 'Button',
										handler : function(btn) {
											btn.disable();
											Ext.getCmp("movieView").findById("centerpanel")
													.add({
																title : 'Office Space',
																html : 'Movie Info'
														});
										}
									}), {
										xtype : 'tbfill'
									}, {
										xtype : "tbbutton",
										cls : "x-btn-text-icon",
										icon : "./images/gisview.png",
										text : "Menu Button",
										menu : [{
													text : "better"
												}, {
													text : "good"
												}, {
													text : "best"
												}]
									}, {
										xtype : "tbsplit",
										text : "Split Button",
										menu : [{
													text : "better"
												}, {
													text : "good"
												}, {
													text : "best"
												}]
									}]
						}, {
							region : 'west',
							id : 'westpanel',
							xtype : 'panel',
							split : true,
							collapsible : true,
							collapseMode : 'mini',
							title : 'Some Info',
							bodyStyle : 'padding:5px;',
							width : 200,
							minSize : 200,
							html : 'West'

						}, {
							region : 'center',
							xtype : 'tabpanel',
							id : 'centerpanel',
							activeTab : 0,
							items : [{
										title : 'Movie Descriptions',
										layout : 'accordion',
										items : [{
													title : 'Office Space',
													autoLoad : 'html/1.txt'
												}, {
													title : 'Super Troopers',
													autoLoad : 'html/3.txt'
												}, {
													title : 'American Beauty',
													autoLoad : 'html/4.txt'
												}]
									}, {
										title : 'Movie Descriptions',
										html : 'Movie Info'
									}]
						}, {

							region : 'east',
							xtype : 'form',
							width : 400,
							bodyStyle : 'padding:5px;',
							items : [{
										xtype : 'textfield',
										fieldLabel : 'Director',
										name : 'director',
										anchor : '100%',
										vtype : 'name'
									}, {
										xtype : 'datefield',
										fieldLabel : 'Released',
										name : 'released',
										disabledDays : [1, 2, 3, 4, 5]
									}, {
										xtype : 'radio',
										fieldLabel : 'Filmed In',
										name : 'filmed_in',
										boxLabel : 'color'
									}]

						}, {
							region : 'south',
							xtype : 'panel',
							html : 'South'
						}]
					});

		});
