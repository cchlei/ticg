    !function(){
        var tpl = "<i class='li'><a class='checker' mid={menuId} href='#'></a>  <span class='info'><span class='label'>{menuName}</span></span></i>";
        var def = {
            onOperate: $.noop
        };
        $.fn.checkTree = function(data,config){
            if(typeof data === "string"){
                var m = this.first();

                //获取主check树已选择数据
                if(data=="getSubCheckData"){
                    return $(".opCont>a.ed", m).map(function(){return $(this).attr("_id")}).toArray().join("|");

                //获取子check已选择数据
                }else if(data=="getCheckData"){
                    return $(".checker.ed", m).map(function(){return $(this).attr("mid")}).toArray().join("|");

                //获取子check混合数据
                }else if(data=="getData"){
                    var arr = $(".checker.ed", m).map(function(){
                        var t = $(this);
                        var dstr = "";
                        dstr+= t.attr("mid");

                        var subChecked =t.next().find(">.opCont .ed");
                        if(subChecked.length){
                            dstr+="("
                            dstr+=subChecked.map(function(){
                                return this.getAttribute("selfId");
                            }).toArray().join(",");
                            dstr+=")"
                        }
                        return dstr;
                    });

                    return arr.toArray().join("|");
                }else{
                    throw "方法未定义";
                }


                //$("#checkB .opCont>a").map(function(){return $(this).attr("_id")}).toArray().join("|")
                //$("#checkB .checker.ed").map(function(){return $(this).attr("mid")}).toArray()
            }
            return this.each(function(){
                var m = $(this);
                var sett = $.extend({},def,config);
                var dt = data.concat();
                m.addClass("checkTree");
                var root = $("<ul></ul>").appendTo(m);
                //root.find("a.checker").click(function(e){
                root.delegate(".checker","click",function(e){
                    var t = $(this);
                    var pli = t.parent()
                    t[t.is(".ed")?"removeClass":"addClass"]("ed");
                    if(t.is(".ed")){
                        t.parents(".li").find(">.checker").addClass("ed");
                        t.parents(".li").find(">.info .opCont").show(120);
                        t.parent().find(".checker").addClass("ed");
                        pli.find(".info .opCont").show(120);

                    }else{
                        var cur = pli;
                        pli.find(".info .opCont").hide(120);
                        pli.find(".checker").removeClass("ed");

                        //递归
                        while(cur.length){
                            if(!cur.find(".ul .checker.ed").length){
                                cur.find(">.checker").removeClass("ed");
                                cur.find(".info .opCont").hide(120);
                            }
                            cur = cur.parents(".li").first();
                        }
                    }
                    e.preventDefault();
                });

                root.delegate(".opCont>a","click",function(e){
                    var t = $(this);
                    //sett.onOperate.call(m, t.attr("_id"),)
                    t[t.is(".ed")?"removeClass":"addClass"]("ed");
                });

                root.attr({
                    unselectable:"on",
                    style:"-moz-user-select:none;",
                    onselectstart:"return false"
                });



                var pool_idToDom = {}, pool_idToData = {};

                //创建每个节点
                $.each(dt,function(k,el){
                    var $el = buidEle(el);
                    pool_idToDom[el.menuId] = $el;
                    pool_idToData[el.menuId] = el;
                });

                //衔接每个节点组成树
                $.each(pool_idToData,function(k,item){
                    var $el = pool_idToDom[k];
                    var par_id = item.parentId;

                    //根节点，直接放置
                    if(par_id=="root"){
                        $el.appendTo(root);
                        return;
                    }


                    var $par_el = pool_idToDom[par_id];

                    //悬空对象,没有父节点,不处理
                    if(!$par_el) return;

                    var par_data = $par_el.data();
                    if(!par_data.ul){
                        par_data.ul = $("<i class='ul'></i>").appendTo($par_el);


                    }
                    par_data.ul.append($el);

                    //如果子默认选中，也选中父
                    if($(">a.ed",$el).length){
                        $el.parents(".checkTree .li").find(">.checker").addClass("ed");
                    }
                });
            });
        }




        /**
         * 创建一个ele
         * @param ele
         */
        function buidEle(eleDt){
            var html = tpl;
            $.each(eleDt,function(k,el){
                html = html.replace(new RegExp("{"+k+"}","g"),el);
            });

            var el = $(html);

            if(eleDt.operate){
                var opCont = $("<div class='opCont'></div>").appendTo(el.find(".info"));
                $.each(eleDt.operate,function(k,el){
                    var clsstr = "";
                    if(el.checked) clsstr=" class='ed'";
                    opCont.append("<a selfId="+ el._id +" _id="+eleDt.menuId+","+el._id +clsstr+">"+el.name+"</a>");
                });
            }

            if(eleDt.checked){
                el.find(".checker").addClass("ed");
                setTimeout(function(){
                    el.find(".opCont").show();
                },300);
            }
            return el;
        }


        /**
         * 根据数组元素的属性，获取元素。仅获取一次
         * @param propName
         * @param val
         * @returns {*}
         */
        Array.prototype.getEl = function(propName,val){
            var m = this;
            var l = m.length;
            for(var i=0;i<l;i++){
                if(m[i][propName] == val){
                    return m[i];
                }
            }

            return -1;
        }


        /**
         * 删除数组某元素 可以多删
         * @param el
         */
        Array.prototype.remove = function(el){
            var m = this;
            var l = m.length;
            for(var i=l-1;i>=0;i--){
                if(m[i] === el){
                    m.splice(i);
                }
            }
        }
    }();