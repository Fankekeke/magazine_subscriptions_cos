<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']">
    <a-row :gutter="8" class="head-info">
      <a-card class="head-info-card">
        <a-col :span="12">
          <!--          <div class="head-info-avatar">-->
          <!--            <img alt="头像" :src="avatar">-->
          <!--          </div>-->
          <div class="head-info-count">
            <div class="head-info-welcome">
              {{welcomeMessage}}
            </div>
            <div class="head-info-desc">
              <p>{{user.roleName ? user.roleName : '暂无角色'}}</p>
            </div>
            <div class="head-info-time">上次登录时间：{{user.lastLoginTime ? user.lastLoginTime : '第一次访问系统'}}</div>
          </div>
        </a-col>
        <a-col :span="12">
          <div>
            <a-row class="more-info" v-if="user.roleId == 74">
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4">
                <head-info title="平台总收益" :content="titleData.orderCode" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="订阅源数量" :content="titleData.orderPrice" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="文章数量" :content="titleData.pharmacyNum" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="用户数量" :content="titleData.staffNum" :center="false"/>
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-card>
    </a-row>
    <home @setTitle="setTitleData"></home>
    <work v-if="user.roleId == 76"></work>
    <a-row :gutter="8" class="count-info" style="margin-top: 15px" v-show="user.roleId == 74">
      <a-col :span="12" class="visit-count-wrapper">
        <a-card class="visit-count" hoverable>
          <apexchart ref="count" type=bar height=300 :options="chartOptions" :series="series" />
        </a-card>
      </a-col>
<!--      <a-col :span="12" class="project-wrapper">-->
<!--        <a-card title="进行中的项目" class="project-card">-->
<!--          <a href="https://github.com/wuyouzhuguli?tab=repositories" target="_blank" slot="extra">所有项目</a>-->
<!--          <table>-->
<!--            <tr>-->
<!--              <td>-->
<!--                <div class="project-avatar-wrapper">-->
<!--                  <a-avatar class="project-avatar">{{projects[0].avatar}}</a-avatar>-->
<!--                </div>-->
<!--                <div class="project-detail">-->
<!--                  <div class="project-name">-->
<!--                    {{projects[0].name}}-->
<!--                  </div>-->
<!--                  <div class="project-desc">-->
<!--                    <p>{{projects[0].des}}</p>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </td>-->
<!--              <td>-->
<!--                <div class="project-avatar-wrapper">-->
<!--                  <a-avatar class="project-avatar">{{projects[1].avatar}}</a-avatar>-->
<!--                </div>-->
<!--                <div class="project-detail">-->
<!--                  <div class="project-name">-->
<!--                    {{projects[1].name}}-->
<!--                  </div>-->
<!--                  <div class="project-desc">-->
<!--                    <p>{{projects[1].des}}</p>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </td>-->
<!--            </tr>-->
<!--          </table>-->
<!--        </a-card>-->
<!--      </a-col>-->
    </a-row>
  </div>
</template>
<script>
import HeadInfo from '@/views/common/HeadInfo'
import {mapState} from 'vuex'
import moment from 'moment'
import Home from './manage/component/home/Home'
import Work from './manage/component/work/Work'
moment.locale('zh-cn')

export default {
  name: 'HomePage',
  components: {Home, Work, HeadInfo},
  data () {
    return {
      titleData: {
        orderCode: 0,
        orderPrice: 0,
        pharmacyNum: 0,
        staffNum: 0
      },
      series: [],
      chartOptions: {
        chart: {
          toolbar: {
            show: false
          }
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '35%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        fill: {
          opacity: 1

        }
      },
      todayIp: '',
      todayVisitCount: '',
      totalVisitCount: '',
      userRole: '',
      userDept: '',
      lastLoginTime: '',
      welcomeMessage: ''
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    }),
    avatar () {
      return `static/avatar/${this.user.avatar}`
    }
  },
  methods: {
    welcome () {
      const date = new Date()
      const hour = date.getHours()
      let time = hour < 6 ? '早上好' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour <= 18 ? '下午好' : '晚上好')))
      return `${time}，${this.user.username}`
    },
    setTitleData (titleData) {
      this.titleData = titleData
    }
  },
  mounted () {
    this.welcomeMessage = this.welcome()
    this.$get(`index/${this.user.username}`).then((r) => {
      let data = r.data.data
      this.todayIp = data.todayIp
      this.todayVisitCount = data.todayVisitCount
      this.totalVisitCount = data.totalVisitCount
      let sevenVisitCount = []
      let dateArr = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenVisitCount) {
          if (o.days === time) {
            contain = true
            sevenVisitCount.push(o.count)
          }
        }
        if (!contain) {
          sevenVisitCount.push(0)
        }
        dateArr.push(time)
      }
      let sevenUserVistCount = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenUserVisitCount) {
          if (o.days === time) {
            contain = true
            sevenUserVistCount.push(o.count)
          }
        }
        if (!contain) {
          sevenUserVistCount.push(0)
        }
      }
      this.$refs.count.updateSeries([
        {
          name: '您',
          data: sevenUserVistCount
        },
        {
          name: '总数',
          data: sevenVisitCount
        }
      ], true)
      this.$refs.count.updateOptions({
        xaxis: {
          categories: dateArr
        },
        title: {
          text: '近七日系统访问记录',
          align: 'left'
        }
      }, true, true)
    }).catch((r) => {
      console.error(r)
      this.$message.error('获取首页信息失败')
    })
  }
}
</script>
<style lang="less">
.home-page {
  .head-info {
    margin-bottom: .5rem;
    .head-info-card {
      padding: .5rem;
      border-color: #f1f1f1;
      .head-info-avatar {
        display: inline-block;
        float: left;
        margin-right: 1rem;
        img {
          width: 5rem;
          border-radius: 2px;
        }
      }
      .head-info-count {
        display: inline-block;
        float: left;
        .head-info-welcome {
          font-size: 1.05rem;
          margin-bottom: .1rem;
        }
        .head-info-desc {
          color: rgba(0, 0, 0, 0.45);
          font-size: .8rem;
          padding: .2rem 0;
          p {
            margin-bottom: 0;
          }
        }
        .head-info-time {
          color: rgba(0, 0, 0, 0.45);
          font-size: .8rem;
          padding: .2rem 0;
        }
      }
    }
  }
  .count-info {
    .visit-count-wrapper {
      padding-left: 0 !important;
      .visit-count {
        padding: .5rem;
        border-color: #f1f1f1;
        .ant-card-body {
          padding: .5rem 1rem !important;
        }
      }
    }
    .project-wrapper {
      padding-right: 0 !important;
      .project-card {
        border: none !important;
        .ant-card-head {
          border-left: 1px solid #f1f1f1 !important;
          border-top: 1px solid #f1f1f1 !important;
          border-right: 1px solid #f1f1f1 !important;
        }
        .ant-card-body {
          padding: 0 !important;
          table {
            width: 100%;
            td {
              width: 50%;
              border: 1px solid #f1f1f1;
              padding: .6rem;
              .project-avatar-wrapper {
                display:inline-block;
                float:left;
                margin-right:.7rem;
                .project-avatar {
                  color: #42b983;
                  background-color: #d6f8b8;
                }
              }
            }
          }
        }
        .project-detail {
          display:inline-block;
          float:left;
          text-align:left;
          width: 78%;
          .project-name {
            font-size:.9rem;
            margin-top:-2px;
            font-weight:600;
          }
          .project-desc {
            color:rgba(0, 0, 0, 0.45);
            p {
              margin-bottom:0;
              font-size:.6rem;
              white-space:normal;
            }
          }
        }
      }
    }
  }
}
</style>
