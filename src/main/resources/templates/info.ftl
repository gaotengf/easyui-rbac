<form class="app-form" id="public_change_info_form">
  <input type="hidden" name="id">
  <div class="field">
    <input readonly="readonly" class="easyui-textbox" label="账号：" name="userName" style="width:100%" value="${s_member.userName}">
  </div>
  <div class="field">
    <input class="easyui-textbox" name="realName" style="width:80%" data-options="label:'姓名：',required:true" value="${s_member.realName}">
    <select class="easyui-combobox" editable="false" data-options="panelHeight:'auto',value:'${s_member.gender}'" name="gender" style="width:18%">
      <option value="BOY">男</option>
      <option value="GIRL">女</option>
    </select>
  </div>
  <div class="field">
    <input class="easyui-textbox" name="telephone" style="width:100%" data-options="label:'电话：',required:true" value="${s_member.telephone}">
  </div>
  <div class="field">
    <input class="easyui-textbox" name="email" style="width:100%" data-options="label:'邮箱：',required:true,validType:'email'" value="${s_member.email}">
  </div>
  <div class="field">
    <input class="easyui-datebox" readonly="readonly" name="hiredate" style="width:100%" data-options="label:'入职日期：',editable:false" value="${s_member.hiredate}">
  </div>
</form>