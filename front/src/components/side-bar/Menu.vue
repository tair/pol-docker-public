<template>
  <!-- sidebar menu: : style can be found in sidebar.less -->
  <div>
    <div class="role-header">Role: {{ this.stateLoggedinUser.role }}</div>
    <ul class="sidebar-menu">
      <router-link
        class="treeview"
        tag="li"
        v-for="(route, i) in roleRoutesSingle"
        :to="route.path"
        :key="i"
        replace
      >
        <a>
          <i class="fa fa-circle-o text-aqua"></i>
          <span>{{ route.title }}</span>
        </a>
      </router-link>
      <li
        class="treeview"
        v-for="(route, i) in roleRoutesWithChildren"
        :class="{ active: route.isActive }"
        :key="'c' + i"
      >
        <a href="#" @click="subMenuClick(route)">
          <i class="fa fa-laptop"></i>
          <span>{{ route.title }}</span>
          <span class="pull-right-container">
            <i class="fa fa-angle-left pull-right"></i>
          </span>
        </a>
        <ul class="treeview-menu">
          <router-link
            v-for="(r, i) in route.children"
            :key="'t' + i"
            :to="r.path"
            tag="li"
            active-class="active"
            replace
          >
            <a>
              <i class="fa fa-dashboard text-light-blue"></i>
              <span>{{ r.title }}</span>
            </a>
          </router-link>
        </ul>
      </li>
    </ul>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import * as login_types from "../../store/types_login";

const types = { ...login_types };

export default {
  name: "Menu",
  data() {
    return {
      adminRoutes: [
        {
          title: "Dashboard",
          icon: "fa fa-dashboard text-light-blue",
          path: "/main/dashboard/admin",
        },
        {
          title: "Users",
          icon: "fa fa-dashboard text-light-blue",
          isActive: false,
          children: [
            {
              path: "/main/users/list",
              title: "View Users",
            },
            {
              path: "/main/users/add",
              title: "Add New User",
            },
            {
              path: "/main/users/actions",
              title: "Admin Actions",
            },
          ],
        },
        {
          title: "Partners",
          icon: "fa fa-dashboard text-light-blue",
          isActive: false,
          children: [
            {
              path: "/main/partner/list",
              title: "View Partners",
            },
            {
              path: "/main/partner/meterlimit",
              title: "Meter Limit",
            },
            {
              path: "/main/partner/meterblacklist",
              title: "Meter Blacklists",
            },
            {
              path: "/main/partner/terms",
              title: "Subscription Terms",
            },
            {
              path: "/main/partner/patterns",
              title: "Partner Patterns",
            },
            {
              path: "/main/partner/uripatterns",
              title: "URI Patterns",
            },
          ],
        },
        {
          title: "Party",
          icon: "fa fa-dashboard text-light-blue",
          isActive: false,
          children: [
            {
              path: "/main/party/consortium",
              title: "View Consortiums",
            },
            {
              path: "/main/party/institution",
              title: "View Institutions",
            },
            {
              path: "/main/party/subscription",
              title: "View Subscriptions",
            },
          ],
        },
      ],
      salesRoutes: [
        {
          title: "Dashboard",
          icon: "fa fa-dashboard text-light-blue",
          path: "/main/dashboard/sales",
        },
        {
          title: "Usage Stats",
          isActive: false,
          children: [
            {
              path: "/main/usage/summary",
              title: "Usage Summary",
            },
            {
              path: "/main/usage/panel",
              title: "View Usage",
            },
            {
              path: "/main/usage/schedule",
              title: "Schedule Usage Reports",
            },
          ],
        },
        {
          title: "Activation Codes",
          isActive: false,
          children: [
            {
              path: "/main/activation/list",
              title: "View Activation Codes",
            },
            {
              path: "/main/activation/add",
              title: "Add Activation Codes",
            },
          ],
        },
      ],
      librarianRoutes: [
        {
          title: "Dashboard",
          icon: "fa fa-dashboard text-light-blue",
          path: "/main/dashboard/librarian",
        },
        {
          title: "Party",
          icon: "fa fa-dashboard text-light-blue",
          isActive: false,
          children: [
            {
              path: "/main/party/librarian",
              title: "View Associated Orgs",
            },
            {
              path: "/main/party/ipranges",
              title: "View IP Ranges",
            },
          ],
        },
        {
          title: "Usage Stats",
          isActive: false,
          children: [
            {
              path: "/main/usage/summary",
              title: "Usage Summary",
            },
            {
              path: "/main/usage/panel",
              title: "View Usage",
            },
            {
              path: "/main/usage/schedule",
              title: "Schedule Usage Reports",
            },
          ],
        },
      ],
      activeRoutes: [],
    };
  },
  computed: {
    ...mapGetters({
      stateLoggedinUser: types.USG_GET_USER,
    }),
    roleRoutesWithChildren() {
      return this.getRoutesByRole(this.stateLoggedinUser.role).filter(
        (route) => route.children
      );
    },
    roleRoutesSingle() {
      return this.getRoutesByRole(this.stateLoggedinUser.role).filter(
        (route) => !route.children
      );
    },
  },
  mounted() {
    this.activeRoutes = this.roleRoutesWithChildren;
  },
  methods: {
    subMenuClick(route) {
      // console.log("route", route);
      if (route.isActive) {
        route.isActive = false;
        return;
      }
      this.activeRoutes.forEach((r) => {
        if (r.isActive) {
          r.isActive = false;
        }
        if (r.title == route.title) {
          r.isActive = true;
        }
      });
    },
    getRoutesByRole(role) {
      // console.log("role", role);
      switch (role) {
        case "Admin":
          return this.adminRoutes;
        case "Sales":
          return this.salesRoutes;
        case "Librarian":
          return this.librarianRoutes;
        default:
          return this.salesRoutes;
      }
    },
    select(text) {
      console.log("selected", text);
    },
  },
};
</script>
<style scoped>
.role-header {
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 12px;
  font-weight: 600;
  color: black;
  padding: 10px 15px;
  text-transform: uppercase;
  background-color: #ffffff;
  margin-left: 20px;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
}
.treeview .active > .treeview-menu {
  display: block;
}
.sidebar-menu > li > a {
  font-size: 15px;
}
</style>
