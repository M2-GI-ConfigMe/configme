/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OrderLineDetailComponent from '@/entities/order-line/order-line-details.vue';
import OrderLineClass from '@/entities/order-line/order-line-details.component';
import OrderLineService from '@/entities/order-line/order-line.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OrderLine Management Detail Component', () => {
    let wrapper: Wrapper<OrderLineClass>;
    let comp: OrderLineClass;
    let orderLineServiceStub: SinonStubbedInstance<OrderLineService>;

    beforeEach(() => {
      orderLineServiceStub = sinon.createStubInstance<OrderLineService>(OrderLineService);

      wrapper = shallowMount<OrderLineClass>(OrderLineDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { orderLineService: () => orderLineServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOrderLine = { id: 123 };
        orderLineServiceStub.find.resolves(foundOrderLine);

        // WHEN
        comp.retrieveOrderLine(123);
        await comp.$nextTick();

        // THEN
        expect(comp.orderLine).toBe(foundOrderLine);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrderLine = { id: 123 };
        orderLineServiceStub.find.resolves(foundOrderLine);

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
