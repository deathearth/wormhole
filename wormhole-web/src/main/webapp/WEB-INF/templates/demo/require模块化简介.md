## requirejs 模块机制
> 官网: http://requirejs.org/

## 声明模块
```javascript
define(模块ID, [依赖], 模块体)
```
当在一个独立js文件内声明时,可省略模块ID,requirejs会自动根据文件路径生成ID
```javascript
define(['jquery'], function ($) {
  // ...
  // 在模块体最后return的对象作为模块的导出对象
  // 当编写可复用组件模块时，需要导出对象
})
```

另一种声明方式，独立进行依赖引入
```javascript
define(function (require, module, exports) {
  // 使用require进行依赖导入
  var $ = require('jquery')
  // 通过exports对象进行模块导出
  exports.xxx = { xxx: 'xxx' }
  // 或使用module.exports,与上面一行是相同的
  module.exports.xxx = { xxx: 'xxx' }
})
```

编写可复用组件
```javascript
define(['jquery'], function ($) {
  var red = function (selector) {
    $(selector).css({ background: '#ff0000' })
  }

  return red
})
```
使用时导入模块使用
```javascript
define(['jquery', 'xxx/red'], function ($, red) {
  red('#text')
})
```

## 相对路径配置
目前requirejs的配置在layout.vm文件内
```javascript
require.config({
  baseUrl: '/velocity/',          // 基础路径
  urlArgs: 'version=$!{version}', // url参数，用于控制正式环境的缓存，version变量在layout.vm的第一行定义
  paths: {                        // 路径简化配置
    pages: 'js/pages',              
    utils: 'js/utils',
    components: 'js/components',
    config: 'config/config',
    jquery: 'lib/jquery/jquery.min',
    lodash: 'lib/lodash/lodash.min',
    bootstrap: 'lib/bootstrap/js/bootstrap.min',
    moment: 'lib/moment/moment.min',
    ztree: 'lib/ztree/js/jquery.ztree.all.min',
    tabhelper: 'js/tab-helper'
  }
})
```

## requirejs运行机制
> require引入的模块，模块体function只会在第一次引入时被执行，并缓存模块导出  
> 多次调用require时，只会从缓存中取出模块导出，不会重复执行模块体  
> 目前项目的页面模块运作机制是由框架读取pageScript配置并require  
> 由于页面模块只需要在页面加载时执行一次，所以不需要进行模块导出  
