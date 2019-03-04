$(function () {
  var $menuWrapper = $('.layout-main')
  $('#menu-button').on('click', function () {
    $menuWrapper.toggleClass('menu-expanded')
  })

  $('.layout-menu').on('click', '.trigger', function () {
    var $li = $(this).parent()
    $li.toggleClass('active-menuitem')
  })

  $('.layout-menu').on('click', '.link', function () {
    var url = $(this).data('url')
    var name = $(this).text()
    window.tabManager.open(url, name)
  })

  var convertMenudata = function (menudata) {
    var menuMap = {}
    var roots = []
    var menus = _.filter(menudata, function (m) { return m.category !== 'a' })
      .sort(function (a, b) { return a.seq - b.seq; })
    menus = _.map(menus, function (m) {
      var menuitem = {
        id: m.id,
        pid: m.pid,
        root: m.pid === 0,
        name: m.name,
        category: m.category,
        route: m.category === 'm' ? m.uri : null,
        children: null,
        icon: m.icon
      };
      menuMap[menuitem.id] = menuitem
      return menuitem
    })
    _.forEach(menus, function (m) {
      if (m.root) {
        roots.push(m);
      } else {
        var parent = menuMap[m.pid]
        if (!parent) {
          console.log('data error:menu parent node not exist')
          return
        }
        if (!parent.children) { parent.children = []; }
        parent.children.push(m);
      }
    })

    return roots;
  }

  var renderMenu = function (data, level) {
    if (!level) level = 1
    var markup = _.map(data, function (d) {
      var h = '<li class="level' + level + '">' +
        '<a href="javascript:" class="' + (d.category === 'd' ? 'trigger' : 'link') + '" ' +
        (d.category === 'm' ? ('data-url="' + d.route + '"') : '') + '">' +
        '<i class="fa fa-fw fa-"></i>' +
        '<span>' + d.name + '</span>' +
        (d.category === 'd' ? '<i class="fa fa-fw fa-caret-down"></i>' : '') +
        '</a>'
      if (d.children) {
        h += '<ul>' + renderMenu(d.children, level + 1) + '</ul>'
      }
      h += '</li>'
      return h
    })
    return markup.join('')
  }

  $.ajax({
    url: '/auth/authz',
    type: 'get',
    dataType: 'json'
  }).done(function (result) {
    var md = convertMenudata(result.data)
    var html = renderMenu(md)

    $('.layout-menu').html(html)
  }).fail(function (response, msg, err) {
    alert(err)
  })

  function watermark(settings) {
    //默认设置
    var defaultSettings = {
      watermark_txt: "",
      watermark_x: 20,//水印起始位置x轴坐标
      watermark_y: 20,//水印起始位置Y轴坐标
      watermark_rows: 30,//水印行数
      watermark_cols: 20,//水印列数
      watermark_x_space: 100,//水印x轴间隔
      watermark_y_space: 80,//水印y轴间隔
      watermark_color: '#000000',//水印字体颜色
      watermark_alpha: 0.06,//水印透明度
      watermark_fontsize: '8px',//水印字体大小
      watermark_font: 'KaiTi',//水印字体
      watermark_width: 80,//水印宽度
      watermark_height: 12,//水印长度
      watermark_angle: 50//水印倾斜度数
    };

    //采用配置项替换默认值，作用类似jquery.extend
    if (arguments.length === 1 && typeof arguments[0] === 'object') {
      var src = arguments[0] || {};
      for (key in src) {
        if (src[key] && defaultSettings[key] && src[key] === defaultSettings[key])
          continue;
        else if (src[key])
          defaultSettings[key] = src[key];
      }
    }

    var oTemp = document.createDocumentFragment();

    //获取页面最大宽度
    var page_width = Math.max(document.body.scrollWidth, document.body.clientWidth);
    //获取页面最大长度
    var page_height = Math.max(document.body.scrollHeight, document.body.clientHeight);

    //如果将水印列数设置为0，或水印列数设置过大，超过页面最大宽度，则重新计算水印列数和水印x轴间隔
    if (defaultSettings.watermark_cols == 0 || (parseInt(defaultSettings.watermark_x + defaultSettings.watermark_width * defaultSettings.watermark_cols + defaultSettings.watermark_x_space * (defaultSettings.watermark_cols - 1)) > page_width)) {
      defaultSettings.watermark_cols = parseInt((page_width - defaultSettings.watermark_x + defaultSettings.watermark_x_space) / (defaultSettings.watermark_width + defaultSettings.watermark_x_space));
      defaultSettings.watermark_x_space = parseInt((page_width - defaultSettings.watermark_x - defaultSettings.watermark_width * defaultSettings.watermark_cols) / (defaultSettings.watermark_cols - 1));
    }
    //如果将水印行数设置为0，或水印行数设置过大，超过页面最大长度，则重新计算水印行数和水印y轴间隔
    if (defaultSettings.watermark_rows == 0 || (parseInt(defaultSettings.watermark_y + defaultSettings.watermark_height * defaultSettings.watermark_rows + defaultSettings.watermark_y_space * (defaultSettings.watermark_rows - 1)) > page_height)) {
      defaultSettings.watermark_rows = parseInt((defaultSettings.watermark_y_space + page_height - defaultSettings.watermark_y) / (defaultSettings.watermark_height + defaultSettings.watermark_y_space));
      defaultSettings.watermark_y_space = parseInt(((page_height - defaultSettings.watermark_y) - defaultSettings.watermark_height * defaultSettings.watermark_rows) / (defaultSettings.watermark_rows - 1));
    }
    var x;
    var y;
    for (var i = 0; i < defaultSettings.watermark_rows; i++) {
      y = defaultSettings.watermark_y + (defaultSettings.watermark_y_space + defaultSettings.watermark_height) * i;
      for (var j = 0; j < defaultSettings.watermark_cols; j++) {
        x = defaultSettings.watermark_x + (defaultSettings.watermark_width + defaultSettings.watermark_x_space) * j;

        var mask_div = document.createElement('div');
        mask_div.className='mask_watermark'
        mask_div.id = 'mask_div' + i + j;
        mask_div.appendChild(document.createTextNode(defaultSettings.watermark_txt));
        //设置水印div倾斜显示
        mask_div.style.webkitTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
        mask_div.style.MozTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
        mask_div.style.msTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
        mask_div.style.OTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
        mask_div.style.transform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
        mask_div.style.visibility = "";
        mask_div.style.position = "absolute";
        mask_div.style.left = x + 'px';
        mask_div.style.top = y + 'px';
        mask_div.style.overflow = "hidden";
        mask_div.style.zIndex = "0";
        //mask_div.style.border="solid #eee 1px";
        mask_div.style.opacity = defaultSettings.watermark_alpha;
        mask_div.style.fontSize = defaultSettings.watermark_fontsize;
        mask_div.style.fontFamily = defaultSettings.watermark_font;
        mask_div.style.color = defaultSettings.watermark_color;
        mask_div.style.textAlign = "center";
        mask_div.style.width = defaultSettings.watermark_width + 'px';
        mask_div.style.height = defaultSettings.watermark_height + 'px';
        mask_div.style.display = "block";
        mask_div.style.padding = "0px";
        mask_div.style.margin = "0px";
        mask_div.style.userSelect = "none"
        mask_div.style.pointerEvents = "none"
        oTemp.appendChild(mask_div);
      };
    };
    document.body.appendChild(oTemp);
  }
  function clearWatermark () {
    var marks = document.getElementsByClassName('mask_watermark')
    while(marks.length > 0) {
      marks[0].parentNode.removeChild(marks[0])
    }
  }

  var watermarkText = $('#watermarkname').val()
  if (document.readyState === "complete") {
    watermark({ watermark_txt: watermarkText })
  } else {
    window.onload=function(){
      watermark({ watermark_txt: watermarkText })
    }
  }
  window.onresize = function () {
    clearWatermark()
    watermark({ watermark_txt: watermarkText, watermark_width: 50 })
  }
})