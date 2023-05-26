<template>
    <div>
      <section class="content-header header-class">
        <h1>
          {{ title }}
          <small>{{ subTitle }}</small>
        </h1>
        <router-link to="/" tag="ol" active-class="active" class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
          <li class="active">{{ title }}</li>
        </router-link>
      </section>

      <section class="header-bar">
        <div v-if="goBackUrl != null" style="margin-bottom: 10px; cursor: pointer" class="pull-right">
          <router-link :to="goBackUrl" tag="a" class="text-fuchsia">
            <i class="fa fa-arrow-circle-left"></i>&nbsp;&nbsp;Go Back
          </router-link>
        </div>

        <div v-if="data != -1 && data == null" class="row">
          <div class="col-md-6">
            <div class="info-box bg-yellow">
              <span class="info-box-icon"><i class="fa fa-exclamation-circle"></i></span>

              <div class="info-box-content">
                <h4>No details found.</h4>
                <h4>ID: {{ id }}</h4>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
</template>

<script>
    export default {
        props: {
          title: {
            type: String,
            required: true
          },
          subTitle: {
            type: String
          },
          id: {},
          data:{
            default: -1   // this should be an JSON object.
          },
          goBackUrl: {
            type: String,
            default: null
          }
        }
    }
</script>

<style scoped>
  .header-class {
    background-color: white;
    border-bottom: 1px solid lightgray;
    padding-bottom: 10px;
    margin-bottom: 10px;
  }

  .header-bar {
    padding-top: 5px;
    padding-left: 15px;
    padding-right: 15px;
    padding-bottom: 0px
  }
</style>
