/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DimensionComponent from '@/entities/dimension/dimension.vue';
import DimensionClass from '@/entities/dimension/dimension.component';
import DimensionService from '@/entities/dimension/dimension.service';

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
  describe('Dimension Management Component', () => {
    let wrapper: Wrapper<DimensionClass>;
    let comp: DimensionClass;
    let dimensionServiceStub: SinonStubbedInstance<DimensionService>;

    beforeEach(() => {
      dimensionServiceStub = sinon.createStubInstance<DimensionService>(DimensionService);
      dimensionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DimensionClass>(DimensionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          dimensionService: () => dimensionServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dimensionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDimensions();
      await comp.$nextTick();

      // THEN
      expect(dimensionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dimensions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      dimensionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDimension();
      await comp.$nextTick();

      // THEN
      expect(dimensionServiceStub.delete.called).toBeTruthy();
      expect(dimensionServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
