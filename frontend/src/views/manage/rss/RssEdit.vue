<template>
  <a-drawer
    title="修改Rss"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">


    <a-form :form="form">
      <a-form-item label='Rss地址' v-bind="formItemLayout">
        <a-input  style="width: 70%" v-decorator="[
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
  import {mapState, mapMutations} from 'vuex'
  const formItemLayout = {
    labelCol: {span: 3},
    wrapperCol: {span: 18}
  }
  export default {
    name: 'UserEdit',
    props: {
      userEditVisiable: {
        default: false
      }
    },
    data() {
      return {
        editor: {
          info: ''
        },
        isClear: false,
        mapId: 'area',
        cardShow: false,
        localPoint: {},
        stayAddress: '',
        local: '',
        localData: [],
        formItemLayout,
        childrenDrawer: false,
        account: {},
        form: this.$form.createForm(this),
        deptTreeData: [],
        keychecks: [],
        roleData: [],
        menuKeys: [],
        userDept: [],
        mockData: [],
        targetKeys: [],
        appInfoData: [],
        roleInfoData: [],
        roleKeys: [],
        orgInfoData: [],
        menuInfoData: [],
        userId: '',
        loading: false,
        loadingBtn: false,
        rssList: [],
        rssUrl: '',
      }
    },
    computed: {
      ...mapState({
        currentUser: state => state.account.user
      })
    },
    created() {

    },
    methods: {
      ...mapMutations({
        setUser: 'account/setUser'
      }),
      getRssList() {
        this.form.validateFields((err, values) => {
          if(values.rssUrl != null) {
            this.loadingBtn = true;
            this.$get('/cos/rss-info/parsing',{
              rssUrl: values.rssUrl
            }).then((r) => {
              this.rssList = r.data.data;
              this.loadingBtn = false;
            })
          }
        })
      },
      change(val) {
        this.editor.info1 = val
      },
      onSearch() {
        let localData = new Array();
        var options = {
          onSearchComplete: (results) =>{
            // 判断状态是否正确
            if (local.getStatus() == BMAP_STATUS_SUCCESS){
              for (var i = 0; i < results.getCurrentNumPois(); i ++){
                if(i==0) {
                  setTimeout(() => {
                    baiduMap.findPoint(results.getPoi(0).point,15);
                  }, 10);
                }
                localData.push(results.getPoi(i));
                if(results.getPoi(i).point != undefined) {
                  baiduMap.localPointAdd(results.getPoi(i));
                }
              }
              this.localData = localData;
              this.cardShow = true;
            }
          }
        };
        var local = new BMap.LocalSearch(baiduMap.rMap(), options);
        local.search(this.local);
      },
      onClose() {
        this.loading = false
        this.form.resetFields()
        this.$emit('close')
      },
      showChildrenDrawer() {
        this.childrenDrawer = true;
      },
      onChildrenDrawerClose() {
        this.childrenDrawer = false
      },
      setFormValues({...user}) {
        this.userId = user.id
        this.editor.info = user.content
        let fields = ['rssUrl', 'rssName','rssAuthor']
        Object.keys(user).forEach((key) => {
          if (fields.indexOf(key) !== -1) {
            this.form.getFieldDecorator(key)
            let obj = {}
            obj[key] = user[key]
            this.form.setFieldsValue(obj)
          }
        })
      },
      onDeptChange(value) {
        this.userDept = value
      },
      handleSubmit() {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.loading = true
            let user = this.form.getFieldsValue()
            user.content = this.editor.info
            user.id = this.userId;
            this.$put('/cos/rss-info', {
              ...user
            }).then((r) => {
              this.loading = false
              this.$emit('success')
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      filterOption(inputValue, option) {
        return option.description.indexOf(inputValue) > -1;
      },
      handleChange(targetKeys, direction, moveKeys) {
        console.log(targetKeys, direction, moveKeys);
        this.targetKeys = targetKeys;
      },
      handleChanges(targetKeys, direction, moveKeys) {
        this.roleKeys = targetKeys;
      },
      handleSearch(dir, value) {
        console.log('search:', dir, value);
      },
      onSelect(keys) {
        this.menuKeys = keys,
          console.log('Trigger Select', keys);
      },
      onCheck(checkedKeys, e) {
        console.log(JSON.stringify(checkedKeys));
      },
    },
    watch: {
      userEditVisiable() {
        if (this.userEditVisiable) {
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
