/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import OrderLineUpdateComponent from '@/entities/order-line/order-line-update.vue';
import OrderLineClass from '@/entities/order-line/order-line-update.component';
import OrderLineService from '@/entities/order-line/order-line.service';

import ClientConfigService from '@/entities/client-config/client-config.service';

import OrderService from '@/entities/order/order.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('OrderLine Management Update Component', () => {
    let wrapper: Wrapper<OrderLineClass>;
    let comp: OrderLineClass;
    let orderLineServiceStub: SinonStubbedInstance<OrderLineService>;

    beforeEach(() => {
      orderLineServiceStub = sinon.createStubInstance<OrderLineService>(OrderLineService);

      wrapper = shallowMount<OrderLineClass>(OrderLineUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          orderLineService: () => orderLineServiceStub,

          clientConfigService: () => new ClientConfigService(),

          orderService: () => new OrderService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.orderLine = entity;
        orderLineServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orderLineServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.orderLine = entity;
        orderLineServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orderLineServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrderLine = { id: 123 };
        orderLineServiceStub.find.resolves(foundOrderLine);
        orderLineServiceStub.retrieve.resolves([foundOrderLine]);

        // WHEN
        comp.beforeRouteEnter({ params: { orderLineId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.orderLine).toBe(foundOrderLine);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
