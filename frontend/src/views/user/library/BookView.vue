<template>
  <a-drawer
    :maskClosable="false"
    width="100%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="show"
    wrapClassName="aa"
    :getContainer="false"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form" layout="vertical">
      <a-row>
        <a-col :span="6" style="height: 100vh;overflow-y: auto">
          <div style="font-size: 13px;font-family: SimHei" v-if="bookData !== null">
            <a-page-header
              style="border: 1px solid rgb(235, 237, 240)"
              :title="bookData.name"
              :sub-title="bookData.tag ? bookData.tag : '- -'"
              @back="onClose"
            />
            <div class="book-info-container">
              <a-carousel autoplay style="height: 150px;" v-if="bookData.images !== undefined && bookData.images">
                <div v-for="(item, index) in bookData.images.split(',')" :key="index" class="carousel-item">
                  <img :src="'http://127.0.0.1:9527/imagesWeb/' + item" alt="封面" class="carousel-image">
                </div>
              </a-carousel>
              <a-carousel autoplay style="height: 150px;" v-else>
                <div class="carousel-item">
                  <img src="http://127.0.0.1:9527/imagesWeb/xxx.png" alt="默认封面" class="carousel-image">
                </div>
              </a-carousel>
              <a-card :bordered="false" class="book-details-card">
                <span slot="title" class="book-title">
                  <div style="font-size: 13px;font-weight: 400" class="book-description">{{ bookData.content }}</div>
                  <div style="font-size: 12px" class="book-update-time">最后更新时间：{{ bookData.updateDate ? bookData.updateDate : '- -' }}</div>
                  <div style="font-size: 12px" class="book-last-chapter">最后内容：{{ bookData.lastChapter ? bookData.lastChapter : '- -' }}</div>
                  <div class="book-author-info">
                    <a-avatar shape="square" :src="'http://127.0.0.1:9527/imagesWeb/' + bookData.authorInfo.images.split(',')[0]" />
                    <span class="author-name">{{ bookData.authorInfo.name }}</span>
                    <a class="follow-button" @click="followUser">
                      <a-icon type="star" theme="twoTone" twoToneColor="#faad14" />
                      关注
                    </a>
                    <a class="like-button" @click="bookLike">
                      <a-icon type="heart" theme="twoTone" twoToneColor="#ff4d4f" />
                      点赞
                    </a>
                  </div>
                </span>
              </a-card>
            </div>
            <br/>
          </div>
          <!-- 内容列表区域美化 -->
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
                  @click="detailOpen(item)"
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
          <div class="comments-section">
            <div class="comments-header">
              <span class="comments-title">杂志评论</span>
              <a-button type="primary" size="small" @click="visible1 = true" class="add-comment-btn">
                <a-icon type="plus" /> 添加评论
              </a-button>
            </div>
            <div class="comments-list">
              <div v-for="(item, index) in evaluateList" :key="index" class="comment-item">
                <a-comment>
                  <a slot="author" class="comment-author">
                    <a-icon type="crown" theme="twoTone" twoToneColor="#faad14" /> {{ item.userName }}
                  </a>
                  <a-avatar
                    slot="avatar"
                    :src="'http://127.0.0.1:9527/imagesWeb/' + item.userImages.split(',')[0]"
                    alt="用户头像"
                    class="comment-avatar"
                  />
                  <p slot="content" class="comment-content">
                    {{ item.content }}
                  </p>
                  <a-tooltip slot="datetime" :title="moment(item.createDate).format('YYYY-MM-DD HH:mm:ss')">
                    <span class="comment-datetime">{{ moment(item.createDate).fromNow() }}</span>
                  </a-tooltip>
                </a-comment>
              </div>
              <div v-if="evaluateList.length === 0" class="no-comments">
                <a-icon type="message" theme="twoTone" twoToneColor="#bfbfbf" style="font-size: 48px;" />
                <p>暂无评论，快来抢沙发吧！</p>
              </div>
            </div>
          </div>
        </a-col>
        <a-col :span="18" style="height: 100vh;overflow-y: auto">
          <!-- 书籍详情内容区域美化 -->
          <div class="book-detail-section" v-if="bookDetail != null">
            <div class="book-detail-header">
              <h2 class="book-detail-title">{{ bookDetail.name }}</h2>
              <div class="book-detail-meta">
        <span class="meta-item">
          <a-icon type="file-text" /> 字数：{{ bookDetail.words }}
        </span>
                <span class="meta-item" v-if="bookDetail.createDate">
          <a-icon type="clock-circle" /> 发布时间：{{ moment(bookDetail.createDate).format('YYYY-MM-DD HH:mm') }}
        </span>
              </div>
            </div>

            <div>
              <div
                v-html="bookDetail.content"
                :style="styleClass"
              >
              </div>
            </div>

            <div class="book-detail-footer">
              <a-button
                type="primary"
                @click="chooseStyle"
                icon="bg-colors"
                size="small"
              >
                切换样式
              </a-button>
            </div>
          </div>

          <div class="no-detail-content" v-else>
            <a-icon type="file-search" theme="twoTone" twoToneColor="#bfbfbf" :style="{ fontSize: '64px' }" />
            <p>请选择左侧内容查看详细信息</p>
          </div>
        </a-col>
      </a-row>
    </a-form>
<!--    <div class="drawer-bootom-button">-->
<!--      <a-button key="submit" type="primary" @click="chooseStyle" style="margin-right: 10px">-->
<!--        选择模板-->
<!--      </a-button>-->
<!--      <a-popconfirm title="确定关闭？" @confirm="onClose" okText="确定" cancelText="取消">-->
<!--        <a-button style="margin-right: .8rem">关闭</a-button>-->
<!--      </a-popconfirm>-->
<!--    </div>-->
    <a-modal
      title="选择格式模板"
      :visible="visible"
      @ok="checkStyle"
      @cancel="handleStyleCancel"
    >
      <a-form :form="form" layout="vertical">
        <a-row :gutter="20">
          <a-col :span="24">
            <a-radio-group button-style="solid" v-model="styleClassTemp">
              <a-radio-button :value="item" v-for="(item, index) in styleList" :key="index">
                {{ item.name }}
              </a-radio-button>
            </a-radio-group>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
    <a-modal
      title="添加数据评论"
      :visible="visible1"
      @ok="checkStyle1"
      @cancel="handleStyleCancel1"
    >
      <a-form :form="form" layout="vertical">
        <a-row :gutter="20">
          <a-col :span="12">
            <a-form-item label='评价分数' v-bind="formItemLayout">
              <a-rate v-decorator="[
            'score',
            { rules: [{ required: true, message: '请输入评价分数!' }] }
            ]" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label='评价内容' v-bind="formItemLayout">
              <a-textarea :rows="6" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入评价内容!' }] }
            ]"/>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </a-drawer>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'bookAdd',
  components: { Editor, Toolbar },
  props: {
    bookAddVisiable: {
      default: false
    },
    bookData: {
      default: null
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.bookAddVisiable
      },
      set: function () {
      }
    }
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
      styleClassTemp: '{}',
      visible: false,
      visible1: false,
      moment,
      evaluateList: [],
      bookDetailList: [],
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      bookList: [],
      styleList: [],
      previewVisible: false,
      previewImage: '',
      editor: null,
      bookDetail: null,
      memberInfo: null,
      html: '',
      title: '',
      toolbarConfig: { },
      editorConfig: { placeholder: '请输入内容...' },
      mode: 'default'
    }
  },
  watch: {
    bookAddVisiable: function (value) {
      console.log(value)
      if (value) {
        this.selectEvaluateBookList(this.bookData.id)
        this.selectBookDetailList(this.bookData.code)
      } else {
        this.bookDetail = null
      }
    }
  },
  mounted () {
    this.selectStyleList()
    this.selectMemberByUserId()
  },
  methods: {
    chooseStyle () {
      this.visible = true
    },
    handleStyleCancel () {
      this.visible = false
    },
    handleStyleCancel1 () {
      this.visible1 = false
    },
    checkStyle () {
      this.styleClass.backgroundColor = this.styleClassTemp.backClass
      this.styleClass.color = this.styleClassTemp.styleClass
      this.visible = false
    },
    checkStyle1 () {
      this.form.validateFields((err, values) => {
        values.bookId = this.bookData.id
        values.userId = this.currentUser.userId
        if (!err) {
          this.$post('/cos/evaluate-info', {
            ...values
          }).then((r) => {
            this.visible1 = false
            this.$message.success('评论成功')
            this.selectEvaluateBookList(this.bookData.id)
          })
        }
      })
    },
    followUser () {
      let data = {userId: this.currentUser.userId, authorId: this.bookData.id}
      this.$post('/cos/follow-info', data).then((r) => {
        this.$message.success('关注成功')
      })
    },
    bookLike () {
      let data = {userId: this.currentUser.userId, authorId: this.bookData.authorId, bookId: this.bookData.id}
      this.$post('/cos/book-like-info', data).then((r) => {
        this.$message.success('点赞成功')
      })
    },
    detailOpen (item) {
      if (this.memberInfo == null && item.checkFlag.toString() === '1') {
        this.$message.warn('该内容需要会员才能查看，请购买会员！')
        return false
      }
      this.$get('/cos/book-detail-info/views/edit', {detailId: item.id, userId: this.currentUser.userId}).then((r) => {
      })
      this.bookDetail = item
    },
    selectMemberByUserId () {
      this.$get(`/cos/member-order-info/member/${this.currentUser.userId}`).then((r) => {
        this.memberInfo = r.data.member
      })
    },
    selectStyleList () {
      this.$get('/cos/style-info/list').then((r) => {
        this.styleList = r.data.data
      })
    },
    selectEvaluateBookList (bookId) {
      this.$get('/cos/evaluate-info/selectListByBookId/list', {bookId}).then((r) => {
        this.evaluateList = r.data.data
      })
    },
    selectBookDetailList (bookId) {
      this.$get('/cos/book-detail-info/list/book', {bookId}).then((r) => {
        this.bookDetailList = r.data.data
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        values.content = this.editor.getHtml()
        if (!err) {
          this.loading = true
          this.$post('/cos/book-detail-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>
<style scoped>
  >>> .ant-drawer-wrapper-body {
    overflow-y: hidden;
  }
  >>> .ant-drawer-body {
    padding: 0 !important;
  }
  >>> .ant-comment-content-detail p {
    white-space: pre-line;
  }
  .book-info-container {
    background: #e8e8e8;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    padding: 15px;
    border-radius: 8px;
  }

  .carousel-item {
    width: 100%;
    height: 150px;
  }

  .carousel-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
  }

  .book-details-card {
    margin-top: 15px;
  }

  .book-title {
    font-size: 14px;
    font-family: SimHei;
    display: block;
  }

  .book-name-tag {
    font-weight: bold;
  }

  .book-description {
    margin-top: 10px;
    color: #555;
  }

  .book-update-time,
  .book-last-chapter {
    margin-top: 10px;
    color: #888;
  }

  .book-author-info {
    margin-top: 20px;
    display: flex;
    align-items: center;
  }

  .author-name {
    font-size: 12px;
    margin-left: 10px;
  }

  .follow-button,
  .like-button {
    font-size: 13px;
    margin-left: auto;
    color: #1890ff;
    cursor: pointer;
  }

  .follow-button:hover,
  .like-button:hover {
    text-decoration: underline;
  }

  @media (max-width: 768px) {
    .book-author-info {
      flex-direction: column;
      align-items: flex-start;
    }

    .follow-button,
    .like-button {
      margin-left: 0;
      margin-top: 10px;
    }
  }

  .comments-section {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
    padding: 20px;
  }

  .comments-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 1px solid #f0f0f0;
  }

  .comments-title {
    font-size: 18px;
    font-weight: 600;
    color: #333;
    font-family: 'SimHei';
  }

  .add-comment-btn {
    border-radius: 4px;
  }

  .comments-list {
    max-height: 600px;
    overflow-y: auto;
  }

  .comment-item {
    border-bottom: 1px solid #f5f5f5;
    padding: 15px 0;
  }

  .comment-item:last-child {
    border-bottom: none;
  }

  .comment-author {
    font-weight: 500;
    color: #555;
  }

  .comment-avatar {
    background: #f0f0f0;
  }

  .comment-content {
    font-size: 14px;
    line-height: 1.6;
    color: #333;
    margin-bottom: 5px;
  }

  .comment-datetime {
    font-size: 12px;
    color: #999;
  }

  .no-comments {
    text-align: center;
    padding: 40px 20px;
    color: #999;
  }

  .no-comments p {
    margin-top: 10px;
    font-size: 14px;
  }

  /* 滚动条样式优化 */
  .comments-list::-webkit-scrollbar {
    width: 6px;
  }

  .comments-list::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 10px;
  }

  .comments-list::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 10px;
  }

  .comments-list::-webkit-scrollbar-thumb:hover {
    background: #a8a8a8;
  }

  .content-list-section {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
    padding: 20px;
  }

  .content-list-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 1px solid #f0f0f0;
  }

  .content-list-title {
    font-size: 18px;
    font-weight: 600;
    color: #333;
    font-family: 'SimHei';
  }

  .content-count {
    font-size: 13px;
    color: #999;
  }

  .content-list-container {
  }

  .content-list-item {
    padding: 15px 0;
    border-bottom: 1px solid #f5f5f5;
    cursor: pointer;
    transition: all 0.3s;
  }

  .content-list-item:hover {
    background-color: #f9f9f9;
    padding-left: 10px;
    padding-right: 10px;
    border-radius: 4px;
  }

  .content-list-item:last-child {
    border-bottom: none;
  }

  .content-item-title {
    display: flex;
    align-items: center;
    font-size: 14px;
  }

  .content-index {
    color: #bfbfbf;
    margin-right: 8px;
    font-size: 12px;
    width: 20px;
  }

  .content-name {
    color: #333;
    font-weight: 500;
    flex: 1;
  }

  .lock-icon {
    margin-left: 8px;
    font-size: 14px;
  }

  .content-meta {
    display: flex;
    font-size: 12px;
    color: #999;
    margin-top: 5px;
  }

  .content-words {
    margin-right: 15px;
  }

  .content-date {
    display: flex;
    align-items: center;
  }

  .content-date .anticon {
    margin-right: 3px;
  }

  .content-words .anticon {
    margin-right: 3px;
  }

  .no-content {
    text-align: center;
    padding: 40px 20px;
    color: #999;
  }

  .no-content p {
    margin-top: 10px;
    font-size: 14px;
  }

  /* 滚动条样式优化 */
  .content-list-container::-webkit-scrollbar {
    width: 6px;
  }

  .content-list-container::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 10px;
  }

  .content-list-container::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 10px;
  }

  .content-list-container::-webkit-scrollbar-thumb:hover {
    background: #a8a8a8;
  }

  /* 响应式优化 */
  @media (max-width: 768px) {
    .content-list-section {
      padding: 15px;
    }

    .content-list-header {
      margin-bottom: 15px;
      padding-bottom: 10px;
    }

    .content-list-item {
      padding: 12px 0;
    }

    .content-meta {
      flex-direction: column;
    }

    .content-words {
      margin-right: 0;
      margin-bottom: 3px;
    }
  }

  .book-detail-section {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 25px;
    margin: 20px;
    min-height: 600px;
  }

  .book-detail-header {
    border-bottom: 1px solid #f0f0f0;
    padding-bottom: 20px;
    margin-bottom: 25px;
  }

  .book-detail-title {
    font-size: 24px;
    font-weight: 600;
    color: #333;
    font-family: 'SimHei';
    margin-bottom: 15px;
  }

  .book-detail-meta {
    display: flex;
    gap: 20px;
  }

  .meta-item {
    font-size: 14px;
    color: #666;
    display: flex;
    align-items: center;
  }

  .meta-item .anticon {
    margin-right: 5px;
    color: #1890ff;
  }

  .book-content-container {
    min-height: 500px;
    padding: 10px 0;
  }

  .book-content {
    font-family: 'SimSun', 'STSong', serif;
    font-size: 16px;
    line-height: 1.8;
    color: #333;
  }

  .book-content >>> p {
    margin-bottom: 1em;
    text-indent: 2em;
  }

  .book-content >>> h1,
  .book-content >>> h2,
  .book-content >>> h3,
  .book-content >>> h4,
  .book-content >>> h5,
  .book-content >>> h6 {
    margin-top: 20px;
    margin-bottom: 15px;
    font-weight: 600;
    color: #222;
  }

  .book-content >>> img {
    max-width: 100%;
    height: auto;
    border-radius: 4px;
    margin: 10px 0;
  }

  .book-detail-footer {
    border-top: 1px solid #f0f0f0;
    padding-top: 20px;
    margin-top: 20px;
    text-align: center;
  }

  .no-detail-content {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 80px 20px;
    margin: 20px;
    text-align: center;
    color: #999;
  }

  .no-detail-content p {
    margin-top: 15px;
    font-size: 16px;
  }

  /* 滚动条样式优化 */
  .book-content-container::-webkit-scrollbar {
    width: 8px;
  }

  .book-content-container::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 10px;
  }

  .book-content-container::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 10px;
  }

  .book-content-container::-webkit-scrollbar-thumb:hover {
    background: #a8a8a8;
  }

  /* 响应式优化 */
  @media (max-width: 768px) {
    .book-detail-section {
      padding: 15px;
      margin: 10px;
    }

    .book-detail-title {
      font-size: 20px;
    }

    .book-detail-meta {
      flex-direction: column;
      gap: 10px;
    }

    .book-content {
      font-size: 15px;
      line-height: 1.7;
    }

    .book-content >>> p {
      text-indent: 1.5em;
    }
  }

  >>> .ant-page-header {
    padding: 10px 24px;
  }
</style>
