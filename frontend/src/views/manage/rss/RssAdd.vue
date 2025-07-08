<template>
  <a-drawer
    title="新增Rss"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">


    <a-form :form="form">
      <a-form-item label='Rss地址' v-bind="formItemLayout">
        <a-input v-model="rssUrl" style="width: 70%" v-decorator="[
        'rssUrl',
        { rules: [{ required: true, message: '请输入名称!' }] }
        ]"/><a-button type="primary" style="width: 30%" @click="getRssList" :loading="loadingBtn">查 看</a-button>
      </a-form-item>
      <a-form-item label='Rss名称' v-bind="formItemLayout">
        <a-input v-decorator="[
        'rssName',
        { rules: [{ required: true, message: '请输入Rss名称!' }] }
        ]"/>
      </a-form-item>
      <a-form-item label='作者' v-bind="formItemLayout">
        <a-input v-decorator="[
        'rssAuthor',
        ]"/>
      </a-form-item>

    </a-form>

    <a-card style="margin-top: 50px">
      <div v-for="(rss,i) in rssList" :key="i">
        <a-divider><a-badge status="processing" /><i>{{rss.publishedDate}}</i></a-divider>
        <a-card hoverable style="width: 100%">
          <img
            v-if="rss.webImg !== undefined && rss.webImg.split(',').length > 0"
            :style="{backgroundImage: 'url(' + rss.webImg.split(',')[0] + ')',height: '150px',backgroundSize: 'cover'}"
            slot="cover"
          />
          <a-card-meta :title="rss.title">
            <template slot="description"
            ><p >{{rss.value}}</p></template
            >
          </a-card-meta>
        </a-card>
      </div>
    </a-card>

    <div class="drawer-bootom-button">
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
  import AFormItem from "ant-design-vue/es/form/FormItem";
  const formItemLayout = {
    labelCol: { span: 3 },
    wrapperCol: { span: 18 }
  }
  export default {
    name: 'UserAdd',
    props: {
      userAddVisiable: {
        default: false
      }
    },
    components: {
      AFormItem,
    },
    data () {
      return {
        editor: {
          info: ''
        },
        loadingBtn: false,
        rssList: [],
        rssUrl: '',
        isClear: false,
        mapId: 'area',
        cardShow: false,
        local: '',
        localData: [],
        childrenDrawer: false,
        user: {
          accountCode: '',
          accountPwd: '',
          accountName: '',
          expireDate: '',
          accountStatus: '',
          defaultApp: ''
        },
        dateFormat: 'YYYY/MM/DD',
        loading: false,
        roleData: [],
        deptTreeData: [],
        mockData: [],
        targetKeys: [],
        appInfoData: [],
        roleInfoData: [],
        roleKeys: [],
        menuInfoData: [],
        orgInfoData: [],
        accountInfo: [],
        menuKeys: [],
        formItemLayout,
        defaultPassword: '1234qwer',
        form: this.$form.createForm(this),
        validateStatus: '',
        help: '',
        localPoint: {},
        stayAddress: ''
      }
    },
    mounted() {

    },
    methods: {
      getRssList() {
        if(this.rssUrl != null) {
          this.loadingBtn = true;
          this.$get('/cos/rss-info/parsing',{
            rssUrl: this.rssUrl
          }).then((r) => {
            this.rssList = r.data.data;
            this.loadingBtn = false;
          })
        }
      },
      change(val) {
        this.editor.info1 = val
      },
      reset () {
        this.loading = false
        this.form.resetFields()
      },
      showChildrenDrawer() {
        this.childrenDrawer = true;
      },
      onChildrenDrawerClose() {
        this.childrenDrawer = false
      },
      onClose () {
        this.reset()
        this.$emit('close')
      },
      handleSubmit () {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.loading = true
            values.content = this.editor.info
            this.$post('/cos/rss-info', {
              ...values
            }).then((r) => {
              this.reset()
              this.$emit('success')
            }).catch(() => {
              this.loading = false
            })

          }
        })
      },
      getMock() {
        const targetKeys = [];
        const mockData = [];
        for (let i = 0; i < 20; i++) {
          const data = {
            key: i.toString(),
            title: `content${i + 1}`,
            description: `description of content${i + 1}`,
            chosen: Math.random() * 2 > 1,
          };
          if (data.chosen) {
            targetKeys.push(data.key);
          }
          mockData.push(data);
        }
        this.mockData = mockData;
        this.targetKeys = targetKeys;
      },
      filterOption(inputValue, option) {
        return option.description.indexOf(inputValue) > -1;
      },
      handleChange(targetKeys, direction, moveKeys) {
        this.targetKeys = targetKeys;
      },
      handleChanges(targetKeys, direction, moveKeys) {
        this.roleKeys = targetKeys;
      },
      handleSearch(dir, value) {
        console.log('search:', dir, value);
      },
      onSelect(keys) {
        this.menuKeys = keys;
        console.log('Trigger Select', keys);
      },
      onCheck(checkedKeys, e) {
        console.log(JSON.stringify(checkedKeys));
      },
    },
    watch: {
      userAddVisiable () {
        if (this.userAddVisiable) {
          this.$get('role').then((r) => {
            this.roleData = r.data.rows
          })
          this.$get('dept').then((r) => {
            this.deptTreeData = r.data.rows.children
          })
        }
      }
    }
  }
</script>
