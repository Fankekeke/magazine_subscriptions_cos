<template>
  <a-card :bordered="false" class="card-area">
    <div>
      <a-row :gutter="30">
        <a-col :span="6" style="margin-bottom: 20px;margin-top: 20px">
          <a-input-search placeholder="搜索订阅源或者作者" enter-button @search="onSearch" />
        </a-col>
        <a-col :span="24" style="background:#ECECEC; padding:30px;margin-top: 30px;margin-bottom: 30px">
          <a-row :gutter="30">
            <a-col :span="4" v-for="(item, index) in bookList" :key="index" v-if="item.status == 1" style="margin-bottom: 20px">
              <div style="background: #e8e8e8">
                <a-carousel autoplay style="height: 150px;" v-if="item.images !== undefined && item.images">
                  <div style="width: 100%;height: 150px" v-for="(item, index) in item.images.split(',')" :key="index">
                    <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 150px">
                  </div>
                </a-carousel>
                <a-carousel autoplay style="height: 150px;" v-else>
                  <img :src="'http://127.0.0.1:9527/imagesWeb/xxx.png'" style="width: 100%;height: 150px">
                </a-carousel>
                <a-card :bordered="false">
                  <span slot="title">
                    <span style="font-size: 14px;font-family: SimHei">
                      {{ item.name }} | <span style="font-size: 13px;color: #5d5a5a">{{ item.tag ? item.tag : '暂无标签' }}</span>
                      <div style="margin: 20px 0px 5px 0px">
                        <a-avatar shape="square" :src="'http://127.0.0.1:9527/imagesWeb/' + item.authorInfo.images.split(',')[0]" />
                        <span style="font-size: 13px;font-family: SimHei;margin-left: 10px">{{ item.authorInfo.name }}</span>
                        <a style="font-size: 13px;font-family: SimHei;float: right;line-height: 32px" @click="viewOpen(item)">查看</a>
                      </div>
                    </span>
                  </span>
                </a-card>
              </div>
            </a-col>
          </a-row>
        </a-col>
      </a-row>
      <book-view
        @close="handlebookAddClose"
        :bookAddVisiable="bookAdd.visiable"
        :bookData="bookAdd.data">
      </book-view>
    </div>
  </a-card>
</template>

<script>
import bookView from './BookView.vue'
import {mapState} from 'vuex'
export default {
  name: 'Work',
  components: {bookView},
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      bookAdd: {
        visiable: false,
        data: null
      },
      series: [{
        name: '浏览量',
        data: []
      }],
      chartOptions: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '内容流量统计',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      bookList: [],
      loading: false
    }
  },
  mounted () {
    this.getWorkStatusList()
  },
  methods: {
    onSearch (value) {
      if (value) {
        this.$get(`/cos/book-info/selectListBySearch/${value}`).then((r) => {
          this.bookList = r.data.data
        })
      } else {
        this.getWorkStatusList()
      }
    },
    viewOpen (row) {
      this.bookAdd.data = row
      this.bookAdd.visiable = true
    },
    handlebookAddClose () {
      this.bookAdd.visiable = false
    },
    selectBookDetailRate (bookId) {
      this.loading = true
      this.$get(`/cos/book-detail-info/rate`, {bookId}).then((r) => {
        this.series[0].data = r.data.data.map(obj => { return obj.views })
        this.chartOptions.xaxis.categories = r.data.data.map(obj => { return obj.name })
        setTimeout(() => {
          this.loading = false
        }, 200)
      })
    },
    getWorkStatusList () {
      // this.$get(`/cos/book-info/selectListDetail`).then((r) => {
      //   this.bookList = r.data.data
      // })
      this.$get(`/cos/book-info/userCfRecommend`, {userId: this.currentUser.userId}).then((r) => {
        this.bookList = r.data.data
      })

    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 150px;
  line-height: 150px;
  overflow: hidden;
}

</style>
