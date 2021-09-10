/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PsuComponent from '@/entities/psu/psu.vue';
import PsuClass from '@/entities/psu/psu.component';
import PsuService from '@/entities/psu/psu.service';

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
  describe('Psu Management Component', () => {
    let wrapper: Wrapper<PsuClass>;
    let comp: PsuClass;
    let psuServiceStub: SinonStubbedInstance<PsuService>;

    beforeEach(() => {
      psuServiceStub = sinon.createStubInstance<PsuService>(PsuService);
      psuServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PsuClass>(PsuComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          psuService: () => psuServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      psuServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPsus();
      await comp.$nextTick();

      // THEN
      expect(psuServiceStub.retrieve.called).toBeTruthy();
      expect(comp.psus[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      psuServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePsu();
      await comp.$nextTick();

      // THEN
      expect(psuServiceStub.delete.called).toBeTruthy();
      expect(psuServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
