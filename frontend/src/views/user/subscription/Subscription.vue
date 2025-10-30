<template>
  <div class="subscription-container">
    <a-row :gutter="12">
      <a-col :span="5">
        <!-- 左侧订阅源列表 -->
        <div class="subscription-sidebar" style="width: 100%">
          <h3>我的订阅</h3>
          <div
            v-for="subscription in subscriptions"
            :key="subscription.id"
            class="subscription-item"
            :class="{ active: currentSubscription && currentSubscription.id === subscription.id }"
            @click="selectSubscription(subscription)"
          >
            <div class="subscription-logo">
              <img :src="'http://127.0.0.1:9527/imagesWeb/' + subscription.bookImages" :alt="subscription.bookName" />
            </div>
            <div class="subscription-info">
              <h4>{{ subscription.bookName }}</h4>
              <p :title="subscription.content">{{ subscription.content }}</p>
            </div>
          </div>
        </div>
      </a-col>
      <a-col :span="7">
        <!-- 中间文章列表 -->
        <div class="article-list" v-if="currentSubscription">
          <h3>{{ currentSubscription.bookName }} 的文章</h3>
          <div class="content-list-section">
            <div class="content-list-header">
              <span class="content-list-title">内容列表</span>
              <span class="content-count">{{ bookDetailList.length }} 篇内容</span>
            </div>

            <div class="content-list-container">
              <a-list
                item-layout="horizontal"
                :pagination="pagination"
                :data-source="bookDetailList"
                :split="false"
              >
                <a-list-item
                  slot="renderItem"
                  slot-scope="item, index"
                  class="content-list-item"
                  @click="selectArticle(item)"
                >
                  <a-list-item-meta>
                    <div slot="title" class="content-item-title">
                      <span class="content-index">{{ index + 1 }}.</span>
                      <span class="content-name content-name-ellipsis">
                        <a-icon
                          type="lock"
                          theme="twoTone"
                          twoToneColor="#faad14"
                          class="lock-icon"
                          v-if="item.checkFlag == 1"
                        />
                        {{ item.name }}
                      </span>
                    </div>
                  </a-list-item-meta>
                  <div class="content-meta" v-if="item.words || item.createDate">
                    <span v-if="item.words" class="content-words">
                      <a-icon type="file-text" /> {{ item.words }} 字
                    </span>
                    <span v-if="item.createDate" class="content-date">
                      <a-icon type="clock-circle" /> {{ moment(item.createDate).format('YYYY-MM-DD') }}
                    </span>
                  </div>
                </a-list-item>
              </a-list>
            </div>
          </div>
        </div>
      </a-col>
      <a-col :span="12">
        <!-- 右侧文章详情 -->
        <div class="article-content" v-if="currentArticle">
          <h2>{{ currentArticle.title }}</h2>
          <div class="article-meta">
            <span>作者: {{ currentSubscription ? currentSubscription.authorName : '' }}</span>
            <span>发布时间: {{ currentArticle.createDate }}</span>
          </div>
          <div class="content" v-html="currentArticle.content"></div>
        </div>
        <!-- 默认提示 -->
        <div class="placeholder" v-if="!currentSubscription">
          <p>请选择一个订阅源查看文章</p>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'Subscription',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      pagination: {
        pageSize: 10
      },
      styleClass: {
        backgroundColor: '',
        color: ''
      },
      subscriptions: [],
      currentSubscription: null,
      articles: [],
      bookDetailList: [],
      currentArticle: null
    }
  },
  methods: {
    queryBookLikeByUserId () {
      this.$get('/cos/book-like-info/queryBookLikeByUserId', {
        userId: this.currentUser.userId
      }).then((r) => {
        this.subscriptions = r.data.data
        if (this.subscriptions.length > 0) {
          this.selectSubscription(this.subscriptions[0])
        }
      })
    },
    selectBookDetailList (bookId) {
      this.$get('/cos/book-detail-info/list/book', {bookId}).then((r) => {
        this.bookDetailList = r.data.data
      })
    },
    selectSubscription (subscription) {
      this.currentSubscription = subscription
      this.currentArticle = null
      // 模拟获取文章列表
      this.selectBookDetailList(subscription.bookCode)
    },
    selectArticle (article) {
      this.currentArticle = article
    }
  },
  mounted () {
    this.queryBookLikeByUserId()
  }
}
</script>

<style scoped>
.article-content {
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.article-content h2 {
  margin: 0 0 25px 0;
  color: #2c3e50;
  font-weight: 700;
  font-size: 26px;
  line-height: 1.4;
}

.article-meta {
  display: flex;
  justify-content: space-between;
  padding: 16px 20px;
  border-bottom: 1px solid #eee;
  color: #7f8c8d;
  font-size: 14px;
  background-color: #fbfbfb;
  border-radius: 8px;
  margin: 0 0 25px 0;
}

.content {
  flex: 1;
  padding: 10px 0;
  line-height: 1.8;
  font-size: 16px;
  color: #34495e;
  overflow-y: auto;
}

.content >>> p {
  margin: 18px 0;
  line-height: 1.8;
}

.content >>> img {
  max-width: 100%;
  border-radius: 10px;
  margin: 15px 0;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.content >>> h1,
.content >>> h2,
.content >>> h3 {
  color: #2c3e50;
  margin: 28px 0 20px 0;
  font-weight: 600;
}

.content >>> h1 {
  font-size: 28px;
}

.content >>> h2 {
  font-size: 24px;
}

.content >>> h3 {
  font-size: 20px;
}

.content >>> ul,
.content >>> ol {
  padding-left: 24px;
  margin: 18px 0;
}

.content >>> li {
  margin: 10px 0;
}

.content >>> a {
  color: #1890ff;
  text-decoration: none;
}

.content >>> a:hover {
  text-decoration: underline;
}

.content >>> blockquote {
  border-left: 4px solid #1890ff;
  padding: 10px 20px;
  margin: 20px 0;
  background-color: #f9f9f9;
  border-radius: 0 8px 8px 0;
}

.content >>> code {
  background-color: #f5f5f5;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: monospace;
}

.content >>> pre {
  background-color: #f5f5f5;
  padding: 15px;
  border-radius: 8px;
  overflow-x: auto;
  margin: 20px 0;
}

.placeholder {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.placeholder p {
  font-size: 20px;
  color: #b4b4b4;
  font-weight: 500;
}

.article-list {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  height: 100%;
  display: flex;
  flex-direction: column;
}

.article-list h3 {
  margin: 0 0 20px 0;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
  color: #2c3e50;
  font-weight: 600;
  font-size: 18px;
}

.content-list-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.content-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 15px;
}

.content-list-title {
  font-weight: 600;
  color: #333;
  font-size: 16px;
}

.content-count {
  font-size: 12px;
  color: #999;
  background: #f5f5f5;
  padding: 3px 10px;
  border-radius: 12px;
}

.content-list-container {
  flex: 1;
  overflow-y: auto;
}

.content-list-item {
  padding: 16px 0 !important;
  border-bottom: 1px dashed #f0f0f0 !important;
  cursor: pointer;
  transition: all 0.2s ease;
}

.content-list-item:hover {
  background-color: #fafafa !important;
  padding-left: 12px !important;
  border-radius: 8px;
  border-bottom-style: solid !important;
}

.content-item-title {
  display: flex;
  align-items: center;
}

.content-index {
  font-size: 12px;
  color: #b4b4b4;
  margin-right: 10px;
  min-width: 24px;
  font-weight: 500;
}

.content-name {
  font-size: 15px;
  color: #333;
  font-weight: 500;
  flex: 1;
}

.content-name-ellipsis {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  word-break: break-word;
}

.lock-icon {
  margin-right: 6px;
  font-size: 14px;
}

.content-meta {
  display: flex;
  gap: 20px;
  margin-top: 10px;
}

.content-words,
.content-date {
  font-size: 12px;
  color: #999;
  display: flex;
  align-items: center;
}

.content-words i,
.content-date i {
  margin-right: 6px;
  font-size: 12px;
}

/* 分页样式优化 */
::v-deep .ant-pagination {
  text-align: center;
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #f0f0f0;
}

::v-deep .ant-pagination-item {
  border-radius: 6px;
  transition: all 0.2s;
}

::v-deep .ant-pagination-item-active {
  background-color: #1890ff;
  border-color: #1890ff;
}

::v-deep .ant-pagination-item:hover {
  border-color: #1890ff;
}

::v-deep .ant-pagination-item-active:hover {
  background-color: #40a9ff;
  border-color: #40a9ff;
}

.subscription-sidebar {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  height: 100%;
  display: flex;
  flex-direction: column;
}

.subscription-sidebar h3 {
  margin: 0 0 20px 0;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
  color: #2c3e50;
  font-weight: 600;
  font-size: 18px;
}

.subscription-item {
  display: flex;
  padding: 16px;
  border-radius: 10px;
  margin-bottom: 15px;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.2, 0, 0.2, 1);
  border: 1px solid transparent;
}

.subscription-item:hover {
  background-color: #f8f9ff;
  border-color: #e0e6ff;
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
}

.subscription-item.active {
  background-color: #f0f5ff;
  border-color: #4096ff;
  box-shadow: 0 2px 8px rgba(64, 150, 255, 0.2);
}

.subscription-logo img {
  width: 64px;
  height: 64px;
  border-radius: 14px;
  object-fit: cover;
  margin-right: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.subscription-info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.subscription-info h4 {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  line-height: 1.3;
}

.subscription-info p {
  margin: 0;
  font-size: 13px;
  color: #7f8c8d;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  word-break: break-word;
}

.subscription-container {
  padding: 20px;
  background-color: #fff;
  min-height: calc(100vh - 60px);
}

.subscription-container ::v-deep .ant-row {
  height: 100%;
}

.subscription-container ::v-deep .ant-col {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>
