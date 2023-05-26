<template>
  <div>
    <app-detail-header
      :id="id"
      :data="data"
      :title="'Subscription Terms'"
      :subTitle="'Details'"
      :go-back-url="'/main/partner/terms'"
    ></app-detail-header>

    <section v-if="data != null" class="content">
      <div class="row">
        <div class="col-md-8">
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title text-primary">Details</h3>
            </div>
            <div class="box-body">
              <div class="row">
                <div class="col-md-6">
                  <app-readonly-field
                    label="Price"
                    :value="data.price | currency"
                  ></app-readonly-field>
                  <app-readonly-field
                    label="Period"
                    :value="data.period"
                  ></app-readonly-field>
                </div>

                <div class="col-md-6">
                  <app-readonly-field
                    label="Discount"
                    :value="data.groupDiscountPercentage + '%'"
                  ></app-readonly-field>
                  <app-readonly-field
                    label="Partner"
                    :value="data.partnerId"
                  ></app-readonly-field>
                </div>
              </div>

              <div class="row">
                <div class="col-md-12 d1">
                  <div class="detail-lbl">Description</div>
                  <div class="detail-lbl-gry" style="min-height: 60px">
                    {{ data.description }}
                  </div>
                </div>
              </div>
            </div>

            <div class="box-footer">
              <div class="pull-right">
                <button class="btn btn-primary">
                  <i class="fa fa-edit"></i>
                  Edit
                </button>
                <button class="btn btn-danger">
                  <i class="fa fa-trash-o"></i>
                  Delete
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";
import authHeader from "../../../../../authHeader";

export default {
  data() {
    return {
      isLoading: false,
      isError: false,
      data: null,

      id: this.$route.params.id,
    };
  },
  components: {},
  watch: {
    $route(to, from) {
      this.id = to.params.id;
      this.loadData();
    },
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      console.log(this.id);
      if (this.id == undefined) return;

      this.isLoading = true;
      let headers = authHeader();
      axios({
        method: "GET",
        url: "/subscriptionTerm/" + this.id,
        headers: headers,
      })
        .then((res) => {
          this.data = res.data;
          this.isLoading = false;

          if (this.data == "") {
            this.data = null;
          }
        })
        .catch((error) => {
          this.isLoading = false;
          this.isError = true;
          console.log("App Error: " + error);
        });
    },
  },
};
</script>

<style scoped></style>
