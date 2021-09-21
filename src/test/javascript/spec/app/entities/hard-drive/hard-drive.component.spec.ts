/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import HardDriveComponent from '@/entities/hard-drive/hard-drive.vue';
import HardDriveClass from '@/entities/hard-drive/hard-drive.component';
import HardDriveService from '@/entities/hard-drive/hard-drive.service';

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
  describe('HardDrive Management Component', () => {
    let wrapper: Wrapper<HardDriveClass>;
    let comp: HardDriveClass;
    let hardDriveServiceStub: SinonStubbedInstance<HardDriveService>;

    beforeEach(() => {
      hardDriveServiceStub = sinon.createStubInstance<HardDriveService>(HardDriveService);
      hardDriveServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<HardDriveClass>(HardDriveComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          hardDriveService: () => hardDriveServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      hardDriveServiceStub.retrieve.resolves({ headers: {}, data: { content: [{ id: 123 }] } });

      // WHEN
      comp.retrieveAllHardDrives();
      await comp.$nextTick();

      // THEN
      expect(hardDriveServiceStub.retrieve.called).toBeTruthy();
      expect(comp.hardDrives[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      hardDriveServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeHardDrive();
      await comp.$nextTick();

      // THEN
      expect(hardDriveServiceStub.delete.called).toBeTruthy();
      expect(hardDriveServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
