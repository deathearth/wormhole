## velocity with bootstrap 版本 帮助文档

### 页面velocity命令
一般页面头部
```html
#set($layout = "/layout/layout.vm")                   ## 使用的布局
#set($pageTitle = "审核详情")                          ## 设置标签页标题
#set($pageScript = "pages/risk/audit/manual/detail")  ## 载入的js
```

if...else判断
```html
#if (${detail.pass} == 1)
<span>通过</span>
#end

#if (${detail.pass} == 1)
<span>通过</span>
#else
<span>不通过</span>
#end

#if (${detail.pass} == 1)
<span>通过</span>
#elseif (${detail.pass} == 0)
<span>不通过</span>
#else
<span>未知</span>
#end
```

foreach循环
```html
#foreach($item in $detail.list)
<li>$!{item.name}</li>
#end
```

注释
```html
## 单行注释
#* 多行
注
释 *#
```

在页面上使用变量
```html
#set($count = 1)
<span>$!{count}</span>
#set($count = $count + 1)
<span>$!{count}</span>
```

Date类型格式化
```html
$dateTool.format("yyyy-MM-dd HH:mm:ss", ${row.cdt})
```

在velocity模版中，也能调用java对象上的方法
```html
<label>身份证号码</label>
<span>$!{idNo.substring(0,3)}****$!{idNo.substring(15,18)}</span> ## 身份证号只显示头尾
```

