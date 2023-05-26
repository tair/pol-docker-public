<template>

  <div>
    <app-detail-header :title="'Meter Limits'"></app-detail-header>

    <section class="content">
      <div class="box box-warning">
        <div class="box-body">

          <form class="form-horizontal">
            <div class="box-body">
              <div class="form-group">
                <label class="col-lg-3 control-label">Blocked Meter</label>
                <div class="col-lg-9">
                  <div class="input-group col-md-7" style="margin-bottom: 7px">
                    <input type="text" class="form-control" value="100">
                    <span class="input-group-addon"><a href="#" class="fa fa-check-square-o text-success"></a></span>
                  </div>
                </div>
              </div>
              <hr>
              <div class="form-group">
                <label class="col-sm-3 control-label">Warning Limits</label>

                <div class="col-sm-9">
                  <div class="input-group col-md-7" style="margin-bottom: 7px">
                    <input type="text" class="form-control" value="100" disabled>
                    <span class="input-group-addon"><a href="#" class="fa fa-trash-o text-danger"></a></span>
                  </div>
                  <div class="input-group col-md-7" style="margin-bottom: 7px">
                    <input type="text" class="form-control" value="100" disabled>
                    <span class="input-group-addon"><a href="#" class="fa fa-trash-o text-danger"></a></span>
                  </div>

                  <div class="input-group col-md-7" style="margin-bottom: 7px">
                    <input type="text" class="form-control" value="100">
                    <span class="input-group-addon"><a href="#" class="fa fa-check-square-o text-success"></a></span>
                  </div>

                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
  </div>

</template>

<script>
  export default {
    data() {
      return {
      }
    },
    components: {
    }
  }
</script>

<style scoped>
</style>
