<template>
  <a-modal title="Rss详细信息"
           @cancel="modelClose"
           :footer="null"
           v-model="scenicVisiable"
           width="70%"
           :destroyOnClose="true"
           :forceRender="true"
           :maskClosable="true">

<!--    <a-row type="flex" justify="space-around">-->
<!--      <a-col :span="6">-->
<!--        <a-card hoverable>-->
<!--          <img-->
<!--            alt="example"-->
<!--            :src="scenicData.webImg[0]"-->
<!--            slot="cover"-->
<!--          />-->
<!--          <template class="ant-card-actions" slot="actions">-->
<!--            <a-icon type="setting"/>-->
<!--            <a-icon type="edit"/>-->
<!--            <a-icon type="ellipsis"/>-->
<!--          </template>-->
<!--        </a-card>-->
<!--      </a-col>-->
<!--    </a-row>-->

    <div style="margin-top: 50px">
      <a-divider/>
      <a-row type="flex" justify="space-around">
        <a-col :span="6">Rss名称：{{scenicData.rssName}}</a-col>
        <a-col :span="4">作者：{{scenicData.author}}</a-col>
        <a-col :span="4">上传时间：{{scenicData.publishedDate}}</a-col>
      </a-row>
      <a-divider/>
      <a-row type="flex" justify="space-around">
        <a-col :span="6">分类：{{scenicData.type}}</a-col>
        <a-col :span="4"><a :href="scenicData.link">跳 转</a></a-col>
        <a-col :span="4"></a-col>
      </a-row>
      <a-divider/>
      <a-row type="flex" justify="space-around">
        <a-col :span="24">介绍：<i v-html="scenicData.value"></i></a-col>
      </a-row>
      <a-divider/>

    </div>

  </a-modal>
</template>

<script>
  export default {
    name: "RssView",
    props: {
      scenicData: {
        require: true
      },
      scenicVisiable: {
        require: true
      },
    },
    data() {
      return {}
    },
    methods: {
      modelClose() {
        this.$emit('close');
      },

    },
  }
</script>

<style scoped>

</style>
