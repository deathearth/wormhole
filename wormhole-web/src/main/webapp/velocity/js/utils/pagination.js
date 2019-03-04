define(['jquery', 'lodash'], function ($, _) {
  var template = '<ul class="pagination" style="vertical-align:middle;">\
      <li class="<%=isFirst?\'disabled\':\'\'%>"><a href="javascript:" role="first"><i class="fa fa-angle-double-left"></i></a></li>\
      <li class="<%=isFirst?\'disabled\':\'\'%>"><a href="javascript:" role="prev"><i class="fa fa-angle-left"></i></a></li>\
      <% _.forEach(visblePages, function (p) { %>\
      <li class="<%= p===currPage ? \'active\' : \'\' %>">\
        <a href="javascript:" role="pager" data-page="<%=p%>" ><%=p%></a>\
      </li>\
      <% }) %>\
      <li class="<%=isLast?\'disabled\':\'\'%>"><a href="javascript:" role="next"><i class="fa fa-angle-right"></i></a></li>\
      <li class="<%=isLast?\'disabled\':\'\'%>"><a href="javascript:" role="last"><i class="fa fa-angle-double-right"></i></a></li>\
    </ul><ul class="pagination" style="vertical-align:middle;padding-left:20px;"><li>共<%=pageCount%>页, <%=rowCount%>条</li></ul>'
  var render = _.template(template)
  var getVisablePages = function (curr, count) {
    var start = curr - 2
    var end = curr + 2
    if (start < 1) {
      start = 1
      end += 1 - start
    }
    if (end > count) {
      end = count
      start += count - end
    }
    start = start < 1 ? 1 : start
    end = end > count ? count : end

    var pages = []
    for (; start <= end; start++) {
      pages.push(start)
    }

    return pages
  }

  var Pagination = function (options) {
    this.$el = options.el
    this.pageSize = options.size || 10
    this.callback = options.loader
    this.bindEvent(this.callback)

    this.set(1, 1)
  }
  Pagination.prototype.bindEvent = function (callback) {
    var $nav = this.$el
    var _this = this
    this.$el.on('click', '[role=pager]', function () {
      var page = $(this).data('page')
      if (page === _this.currPage.toString()) { return }
      callback(page, _this)
    })
    this.$el.on('click', '[role=next]', function () {
      if (_this.isLast) { return }
      callback(_this.currPage + 1, _this)
    })
    this.$el.on('click', '[role=last]', function () {
      if (_this.isLast) { return }
      callback(_this.pageCount, _this)
    })
    this.$el.on('click', '[role=prev]', function () {
      if (_this.isFirst) { return }
      callback(_this.currPage - 1, _this)
    })
    this.$el.on('click', '[role=first]', function () {
      if (_this.isFirst) { return }
      callback(1, _this)
    })
  }
  Pagination.prototype.set = function (currPage, rowCount) {
    this.currPage = currPage
    this.rowCount = rowCount
    this.pageCount = Math.ceil(rowCount / this.pageSize)
    this.isFirst = currPage === 1
    this.isLast = currPage === this.pageCount
    this.visblePages = getVisablePages(currPage, this.pageCount)

    var markup = render(this)

    this.$el.html(markup)
  }
  Pagination.prototype.reload = function () {
    this.callback(this.currPage, this)
  }
  Pagination.prototype.goto = function (page) {
    this.callback(page, this)
  }

  return Pagination
})