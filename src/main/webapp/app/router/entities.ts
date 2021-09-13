import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Address = () => import('@/entities/address/address.vue');
// prettier-ignore
const AddressUpdate = () => import('@/entities/address/address-update.vue');
// prettier-ignore
const AddressDetails = () => import('@/entities/address/address-details.vue');
// prettier-ignore
const Product = () => import('@/entities/product/product.vue');
// prettier-ignore
const ProductUpdate = () => import('@/entities/product/product-update.vue');
// prettier-ignore
const ProductDetails = () => import('@/entities/product/product-details.vue');
// prettier-ignore
const Order = () => import('@/entities/order/order.vue');
// prettier-ignore
const OrderUpdate = () => import('@/entities/order/order-update.vue');
// prettier-ignore
const OrderDetails = () => import('@/entities/order/order-details.vue');
// prettier-ignore
const OrderLine = () => import('@/entities/order-line/order-line.vue');
// prettier-ignore
const OrderLineUpdate = () => import('@/entities/order-line/order-line-update.vue');
// prettier-ignore
const OrderLineDetails = () => import('@/entities/order-line/order-line-details.vue');
// prettier-ignore
const ClientConfig = () => import('@/entities/client-config/client-config.vue');
// prettier-ignore
const ClientConfigUpdate = () => import('@/entities/client-config/client-config-update.vue');
// prettier-ignore
const ClientConfigDetails = () => import('@/entities/client-config/client-config-details.vue');
// prettier-ignore
const Cpu = () => import('@/entities/cpu/cpu.vue');
// prettier-ignore
const CpuUpdate = () => import('@/entities/cpu/cpu-update.vue');
// prettier-ignore
const CpuDetails = () => import('@/entities/cpu/cpu-details.vue');
// prettier-ignore
const Gpu = () => import('@/entities/gpu/gpu.vue');
// prettier-ignore
const GpuUpdate = () => import('@/entities/gpu/gpu-update.vue');
// prettier-ignore
const GpuDetails = () => import('@/entities/gpu/gpu-details.vue');
// prettier-ignore
const HardDrive = () => import('@/entities/hard-drive/hard-drive.vue');
// prettier-ignore
const HardDriveUpdate = () => import('@/entities/hard-drive/hard-drive-update.vue');
// prettier-ignore
const HardDriveDetails = () => import('@/entities/hard-drive/hard-drive-details.vue');
// prettier-ignore
const Ram = () => import('@/entities/ram/ram.vue');
// prettier-ignore
const RamUpdate = () => import('@/entities/ram/ram-update.vue');
// prettier-ignore
const RamDetails = () => import('@/entities/ram/ram-details.vue');
// prettier-ignore
const Psu = () => import('@/entities/psu/psu.vue');
// prettier-ignore
const PsuUpdate = () => import('@/entities/psu/psu-update.vue');
// prettier-ignore
const PsuDetails = () => import('@/entities/psu/psu-details.vue');
// prettier-ignore
const Ventirad = () => import('@/entities/ventirad/ventirad.vue');
// prettier-ignore
const VentiradUpdate = () => import('@/entities/ventirad/ventirad-update.vue');
// prettier-ignore
const VentiradDetails = () => import('@/entities/ventirad/ventirad-details.vue');
// prettier-ignore
const Dimension = () => import('@/entities/dimension/dimension.vue');
// prettier-ignore
const DimensionUpdate = () => import('@/entities/dimension/dimension-update.vue');
// prettier-ignore
const DimensionDetails = () => import('@/entities/dimension/dimension-details.vue');
// prettier-ignore
const Mbe = () => import('@/entities/mbe/mbe.vue');
// prettier-ignore
const MbeUpdate = () => import('@/entities/mbe/mbe-update.vue');
// prettier-ignore
const MbeDetails = () => import('@/entities/mbe/mbe-details.vue');
// prettier-ignore
const ComputerCase = () => import('@/entities/computer-case/computer-case.vue');
// prettier-ignore
const ComputerCaseUpdate = () => import('@/entities/computer-case/computer-case-update.vue');
// prettier-ignore
const ComputerCaseDetails = () => import('@/entities/computer-case/computer-case-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/address',
    name: 'Address',
    component: Address,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/address/new',
    name: 'AddressCreate',
    component: AddressUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/address/:addressId/edit',
    name: 'AddressEdit',
    component: AddressUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/address/:addressId/view',
    name: 'AddressView',
    component: AddressDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/product',
    name: 'Product',
    component: Product,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/product/new',
    name: 'ProductCreate',
    component: ProductUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/product/:productId/edit',
    name: 'ProductEdit',
    component: ProductUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/product/:productId/view',
    name: 'ProductView',
    component: ProductDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order',
    name: 'Order',
    component: Order,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order/new',
    name: 'OrderCreate',
    component: OrderUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order/:orderId/edit',
    name: 'OrderEdit',
    component: OrderUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order/:orderId/view',
    name: 'OrderView',
    component: OrderDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order-line',
    name: 'OrderLine',
    component: OrderLine,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order-line/new',
    name: 'OrderLineCreate',
    component: OrderLineUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order-line/:orderLineId/edit',
    name: 'OrderLineEdit',
    component: OrderLineUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/order-line/:orderLineId/view',
    name: 'OrderLineView',
    component: OrderLineDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/client-config',
    name: 'ClientConfig',
    component: ClientConfig,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/client-config/new',
    name: 'ClientConfigCreate',
    component: ClientConfigUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/client-config/:clientConfigId/edit',
    name: 'ClientConfigEdit',
    component: ClientConfigUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/client-config/:clientConfigId/view',
    name: 'ClientConfigView',
    component: ClientConfigDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/cpu',
    name: 'Cpu',
    component: Cpu,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/cpu/new',
    name: 'CpuCreate',
    component: CpuUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/cpu/:cpuId/edit',
    name: 'CpuEdit',
    component: CpuUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/cpu/:cpuId/view',
    name: 'CpuView',
    component: CpuDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/gpu',
    name: 'Gpu',
    component: Gpu,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/gpu/new',
    name: 'GpuCreate',
    component: GpuUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/gpu/:gpuId/edit',
    name: 'GpuEdit',
    component: GpuUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/gpu/:gpuId/view',
    name: 'GpuView',
    component: GpuDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/hard-drive',
    name: 'HardDrive',
    component: HardDrive,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/hard-drive/new',
    name: 'HardDriveCreate',
    component: HardDriveUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/hard-drive/:hardDriveId/edit',
    name: 'HardDriveEdit',
    component: HardDriveUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/hard-drive/:hardDriveId/view',
    name: 'HardDriveView',
    component: HardDriveDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ram',
    name: 'Ram',
    component: Ram,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ram/new',
    name: 'RamCreate',
    component: RamUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ram/:ramId/edit',
    name: 'RamEdit',
    component: RamUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ram/:ramId/view',
    name: 'RamView',
    component: RamDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/psu',
    name: 'Psu',
    component: Psu,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/psu/new',
    name: 'PsuCreate',
    component: PsuUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/psu/:psuId/edit',
    name: 'PsuEdit',
    component: PsuUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/psu/:psuId/view',
    name: 'PsuView',
    component: PsuDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ventirad',
    name: 'Ventirad',
    component: Ventirad,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ventirad/new',
    name: 'VentiradCreate',
    component: VentiradUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ventirad/:ventiradId/edit',
    name: 'VentiradEdit',
    component: VentiradUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/ventirad/:ventiradId/view',
    name: 'VentiradView',
    component: VentiradDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/dimension',
    name: 'Dimension',
    component: Dimension,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/dimension/new',
    name: 'DimensionCreate',
    component: DimensionUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/dimension/:dimensionId/edit',
    name: 'DimensionEdit',
    component: DimensionUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/dimension/:dimensionId/view',
    name: 'DimensionView',
    component: DimensionDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/mbe',
    name: 'Mbe',
    component: Mbe,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/mbe/new',
    name: 'MbeCreate',
    component: MbeUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/mbe/:mbeId/edit',
    name: 'MbeEdit',
    component: MbeUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/mbe/:mbeId/view',
    name: 'MbeView',
    component: MbeDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/computer-case',
    name: 'ComputerCase',
    component: ComputerCase,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/computer-case/new',
    name: 'ComputerCaseCreate',
    component: ComputerCaseUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/computer-case/:computerCaseId/edit',
    name: 'ComputerCaseEdit',
    component: ComputerCaseUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/computer-case/:computerCaseId/view',
    name: 'ComputerCaseView',
    component: ComputerCaseDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
