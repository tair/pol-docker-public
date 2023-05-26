import LoginPage from "./views/LoginPage";
import MainPage from "./views/MainPage";
import RegisterPage from "./views/RegisterPage";

import Activate from "./components/user-activation/Activate.vue";
import Recover from "./components/login/Recover.vue";

import DashboardAdmin from "./views/dashboard/DashboardAdmin";
import DashboardLibrarian from "./views/dashboard/DashboardLibrarian";
import DashboardSales from "./views/dashboard/DashboardSales";
import DashboardGeneral from "./views/dashboard/DashboardGeneral";

import UsageSummary from "./views/usage/UsageSummary";
import UsagePanel from "./views/usage/UsagePanel";
import UsageReport from "./views/usage/UsageReport";

import SubscriptionPanel from "./views/party/SubscriptionPanel";

import ActivationPanel from "./views/activation/ActivationPanel";
import ActivationAdd from "./views/activation/ActivationAdd";

import UsersPanel from "./views/users/UsersPanel";
import UserAdd from "./views/users/UserAdd";
import UsersAdminActions from "./views/users/UsersAdminActions";

import InstitutionPanel from "./views/party/InstitutionPanel";
import ConsortiumPanel from "./views/party/ConsortiumPanel";
import LibrarianPartyPanel from "./views/party/LibrarianPartyPanel";
import IPRangePanel from "./views/party/IPRangePanel";
import PartnerPanel from "./views/partner/PartnerPanel";
import MeterLimitPanel from "./views/partner/MeterLimitPanel";

import ResetPassword from "./components/recover/ResetPassword.vue";

import Activation from "./components/paywall/activation/Activation";
// import Subscription from "./components/paywall/subscription/Subscription";
import Usage from "./components/paywall/usage/Usage";
import Sessions from "./components/paywall/sessions/Sessions";
import Summary from "./components/paywall/summary/Summary";

import UserProfile from "./components/userprofile/UserProfile.vue";
import PartnerMeterBlacklist from "./components/paywall/partner/sections/PartnerMeterBlacklist";
import PartnerSubscriptionTerms from "./components/paywall/partner/sections/PartnerSubscriptionTerms";
import PartnerUriPatterns from "./components/paywall/partner/sections/PartnerUriPatterns";
import PartnerPattern from "./components/paywall/partner/sections/PartnerPattern";
import PartnerSubscriptionTermsDetail from "./components/paywall/partner/sections/details/PartnerSubscriptionTermsDetail";

export const routes = [
  { path: "/", redirect: "/login" },
  { path: "/login", component: LoginPage },
  { path: "/register", component: RegisterPage },
  {
    path: "/activate",
    component: Activate,
    props: (route) => ({
      email: route.query.email,
      activationCode: route.query.activationCode,
    }),
  },
  { path: "/user", component: UserProfile },
  {
    path: "/reset",
    component: ResetPassword,
    props: (route) => ({
      email: route.query.email,
      passwordResetToken: route.query.passwordResetToken,
      password: route.query.password,
    }),
  },
  { path: "/recover", component: Recover },
  {
    path: "/main",
    component: MainPage,
    children: [
      {
        path: "dashboard/general",
        component: DashboardGeneral,
      },
      {
        path: "dashboard/admin",
        component: DashboardAdmin,
      },
      {
        path: "dashboard/librarian",
        component: DashboardLibrarian,
      },
      {
        path: "dashboard/sales",
        component: DashboardSales,
      },
      {
        path: "usage/summary",
        component: UsageSummary,
      },
      {
        path: "usage/panel",
        component: UsagePanel,
      },
      {
        path: "usage/schedule",
        component: UsageReport,
      },
      {
        path: "activation/list",
        component: ActivationPanel,
      },
      {
        path: "activation/add",
        component: ActivationAdd,
      },
      {
        path: "users/list",
        component: UsersPanel,
      },
      {
        path: "users/add",
        component: UserAdd,
      },
      {
        path: "users/actions",
        component: UsersAdminActions,
      },
      {
        path: "party/institution",
        component: InstitutionPanel,
      },
      {
        path: "party/consortium",
        component: ConsortiumPanel,
      },
      {
        path: "party/subscription",
        component: SubscriptionPanel,
      },
      {
        path: "party/librarian",
        component: LibrarianPartyPanel,
      },
      {
        path: "party/ipranges",
        component: IPRangePanel,
      },
      {
        path: "partner/list",
        component: PartnerPanel,
      },
      {
        path: "partner/meterlimit",
        component: MeterLimitPanel,
      },
      {
        path: "partner/meterblacklist",
        component: PartnerMeterBlacklist,
      },
      {
        path: "partner/terms",
        component: PartnerSubscriptionTerms,
      },
      {
        path: "partner/terms/detail/:id",
        component: PartnerSubscriptionTermsDetail,
      },
      {
        path: "partner/patterns",
        component: PartnerPattern,
      },
      {
        path: "partner/uripatterns",
        component: PartnerUriPatterns,
      },
      { path: "/activation", component: Activation },
      { path: "/usage", component: Usage },
      { path: "/sessions", component: Sessions },
      { path: "/summary", component: Summary },
    ],
  },
];
