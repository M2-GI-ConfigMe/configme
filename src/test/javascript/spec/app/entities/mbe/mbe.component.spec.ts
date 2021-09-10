/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MbeComponent from '@/entities/mbe/mbe.vue';
import MbeClass from '@/entities/mbe/mbe.component';
import MbeService from '@/entities/mbe/mbe.service';

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
  describe('Mbe Management Component', () => {
    let wrapper: Wrapper<MbeClass>;
    let comp: MbeClass;
    let mbeServiceStub: SinonStubbedInstance<MbeService>;

    beforeEach(() => {
      mbeServiceStub = sinon.createStubInstance<MbeService>(MbeService);
      mbeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MbeClass>(MbeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          mbeService: () => mbeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      mbeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMbes();
      await comp.$nextTick();

      // THEN
      expect(mbeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.mbes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      mbeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMbe();
      await comp.$nextTick();

      // THEN
      expect(mbeServiceStub.delete.called).toBeTruthy();
      expect(mbeServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
