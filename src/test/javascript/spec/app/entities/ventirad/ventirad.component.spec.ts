/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import VentiradComponent from '@/entities/ventirad/ventirad.vue';
import VentiradClass from '@/entities/ventirad/ventirad.component';
import VentiradService from '@/entities/ventirad/ventirad.service';

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
  describe('Ventirad Management Component', () => {
    let wrapper: Wrapper<VentiradClass>;
    let comp: VentiradClass;
    let ventiradServiceStub: SinonStubbedInstance<VentiradService>;

    beforeEach(() => {
      ventiradServiceStub = sinon.createStubInstance<VentiradService>(VentiradService);
      ventiradServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VentiradClass>(VentiradComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ventiradService: () => ventiradServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ventiradServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVentirads();
      await comp.$nextTick();

      // THEN
      expect(ventiradServiceStub.retrieve.called).toBeTruthy();
      expect(comp.ventirads[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ventiradServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeVentirad();
      await comp.$nextTick();

      // THEN
      expect(ventiradServiceStub.delete.called).toBeTruthy();
      expect(ventiradServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
