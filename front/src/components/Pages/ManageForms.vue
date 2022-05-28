<template>
  <el-card>
    <el-row>
      <el-col :span="4"></el-col>
      <el-col :span="16">
        <h1 title="123">我的待处理表单</h1>
      </el-col>
      <el-col :span="4">
        <el-button
          style="padding: 20px"
          title="新建空白表单模板"
          type="primary"
          @click="ToCreateForm"
          plain
          >新建模板</el-button
        >
      </el-col>
    </el-row>
    <el-table :data="formInfo" style="width: 100%">
      <el-table-column prop="id" label="表单编号" width="180" align="center" />
      <el-table-column
        prop="name"
        label="表单名称"
        width="180"
        align="center"
      />
      <el-table-column
        prop="description"
        label="Address"
        width="180"
        align="center"
      />
      <el-table-column label="Operations" align="center">
        <template #default="scope">
          <el-button
            size="small"
            @click="ToPreviewForm(scope.$index, scope.row.id)"
            title="查看根据模板生成的表单"
            type="success"
            plain
            round
            >预览模板</el-button
          >
          <el-button
            size="small"
            @click="ToChangeForm(scope.$index, scope.row.id)"
            title="查看根据模板生成的表单"
            type="success"
            plain
            round
            >编辑模板</el-button
          >
          <el-button
            size="small"
            @click="ToPublishTemplate(scope.$index, scope.row.id)"
            title="根据模板创建并发布一个表单"
            type="primary"
            plain
            round
            >发布模板</el-button
          >
          <el-button
            size="small"
            type="danger"
            @click="ToDeleteTemplate(scope.$index, scope.row.id)"
            title="彻底删除此表单模板"
            plain
            round
            >删除模板</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-divider style="margin-top: 0"></el-divider>
  </el-card>
</template>