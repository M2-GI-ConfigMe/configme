/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OrderLineComponent from '@/entities/order-line/order-line.vue';
import OrderLineClass from '@/entities/order-line/order-line.component';
import OrderLineService from '@/entities/order-line/order-line.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('OrderLine Management Component', () => {
    let wrapper: Wrapper<OrderLineClass>;
    let comp: OrderLineClass;
    let orderLineServiceStub: SinonStubbedInstance<OrderLineService>;

    beforeEach(() => {
      orderLineServiceStub = sinon.createStubInstance<OrderLineService>(OrderLineService);
      orderLineServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OrderLineClass>(OrderLineComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          orderLineService: () => orderLineServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      orderLineServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOrderLines();
      await comp.$nextTick();

      // THEN
      expect(orderLineServiceStub.retrieve.called).toBeTruthy();
      expect(comp.orderLines[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      orderLineServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOrderLine();
      await comp.$nextTick();

      // THEN
      expect(orderLineServiceStub.delete.called).toBeTruthy();
      expect(orderLineServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
