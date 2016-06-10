define(function (require, exports, module) {
    /*var co = require("__/js/comm");*/
    var u = require("_/tr_util");
    var ht = $("html");

    $(function(){

        //账号退出
        var signout = ({
            init:function(){
                var m = this;
                m.el = $(".top");
                m.el.delegate(">.right","click",function(){
                    var mm = $(this);
                    mm.toggleClass("cur");
                })
                return m;
            }

        }).init();

        //一级主菜单
        var nav = ({
            init:function(index){
                var m = this;
                m.el = $(".nav");
                //avalon菜单
                m.vm =avalon.define({
                    $id : "menu",
                    list:[],
                    shousuo:"",
                    three_shousuo:"",
                    is_three_menu_show:function(eee){
                        return eee.light && eee.rows && eee.rows.length;
                    },
                    openmenu:function(e){
                        m.vm.shousuo = !m.vm.shousuo;
                        $(".threemenu").toggle();
                        ht.toggleClass("shousuo_submenu");
                    },

                    nodeclick:function(el){

                        if(el.rows && el.rows.length){
                            m.vm.nodeclick(el.rows[0])
                            return;
                        }

                        if(el.url){

                            m.open_url(el.url);

                            $.each(m.__menu_obj_cache,function(k,el){
                                el.light = false;
                            })

                            el.light = true;


                            !function(_el){
                                var ac = arguments.callee;
                                var parent = m.__dic_mennuid_menu[_el.__parentid];
                                if(parent){
                                    parent.light = true;
                                    ac(parent);
                                }

                                //第二层有没有子菜单
                                if(_el.level ==2){
                                    if(_el.rows && _el.rows.length){
                                        ht.addClass("level_2_has_submenu");
                                    }else{
                                        ht.removeClass("level_2_has_submenu");
                                    }
                                }
                            }(el);



                        }


                    }

                })
                avalon.scan();

                u.ajax(
                    u.pagevar("menu_list","../data/menulist.json"),function(t,data){
                       if(t.vl()){



                           var menu_id_counter = 0;

                           data.__id = -1;

                           !function (menu,level) {
                               var ac = arguments.callee;
                               $.each(menu.rows,function(k,el){
                                   el.light = false;
                                   el.level = level;
                                   el.__id = menu_id_counter++;
                                   el.__parentid = menu.__id;
                                   if(el.rows){
                                       ac(el,level+1)
                                   }
                               });
                           }(data,1);

                           m.vm.list = data.rows;

                           !function (_list) {
                               var ac = arguments.callee;
                               $.each(_list, function (k, el) {
                                   m.__menu_obj_cache.push(el);
                                   m.__dic_mennuid_menu[el.__id] = el;
                                   if (el.rows) {
                                       ac(el.rows);
                                   }
                               })
                           }(m.vm.list);

                           //console.log(m.vm.list.$model)
                           m.vm.nodeclick( data);
                       }
                    }
                );

                //主菜单折叠
                m.el.delegate(">h4","click",function(){
                    var mm = $(this);
                    ht.toggleClass("left_menu_collapse");
                    mm.parent().toggleClass("cur");
                });

                m.el.delegate("h3","click",function(e){
                    if($(e.target).is("em")){
                        return;
                    }else{
                        var mm = $(this);
                        mm.toggleClass("curr");
                        mm.next().toggle();
                        ht.toggleClass("sanjimenu");
                    }
                });

                //三级菜单折叠
                var three_openclose=(".threemenu>div>p>a");
                m.el.delegate(three_openclose,"click",function(e){
                    var m = $(this);
                    m.toggleClass("on");
                    m.parent("p").next("ul").slideToggle();
                });


                m.$iframe = $("#main_iframe");
                m.iframe = m.$iframe[0];

                return m;
            },

            open_url:function(href){
                var m = this;
                if(href == m.$iframe.attr("src"))   return;

                m.$iframe.attr("src",href);
            },

            __dic_mennuid_menu:{},

            __menu_obj_cache:[]

        }).init();




    });

    return {};
});