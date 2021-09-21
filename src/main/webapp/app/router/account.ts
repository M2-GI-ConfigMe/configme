import { Authority } from '@/shared/security/authority';

const Register = () => import('@/account/register/register.vue');
const Activate = () => import('@/account/activate/activate.vue');
const ResetPasswordInit = () => import('@/account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('@/account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('@/account/change-password/change-password.vue');
const AccountSettings = () => import('@/account/settings/settings.vue');
const AccountHome = () => import('@/account/home.vue');
const AccountOrders = () => import('@/account/orders/orders.vue');

export default [
  {
    path: '/register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/account/activate',
    name: 'Activate',
    component: Activate,
  },
  {
    path: '/account/reset/request',
    name: 'ResetPasswordInit',
    component: ResetPasswordInit,
  },
  {
    path: '/account/reset/finish',
    name: 'ResetPasswordFinish',
    component: ResetPasswordFinish,
  },
  {
    path: '/account/password',
    name: 'ChangePassword',
    component: ChangePassword,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/account/',
    name: 'Settings',
    component: AccountHome,
    redirect: '/account/settings',
    meta: { authorities: [Authority.USER] },
    children: [
      { path: 'settings', component: AccountSettings, meta: { authorities: [Authority.USER] } },
      { path: 'orders', component: AccountOrders, meta: { authorities: [Authority.USER] } },
    ],
  },
];
