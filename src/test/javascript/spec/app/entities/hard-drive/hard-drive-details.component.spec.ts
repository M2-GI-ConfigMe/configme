/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import HardDriveDetailComponent from '@/entities/hard-drive/hard-drive-details.vue';
import HardDriveClass from '@/entities/hard-drive/hard-drive-details.component';
import HardDriveService from '@/entities/hard-drive/hard-drive.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('HardDrive Management Detail Component', () => {
    let wrapper: Wrapper<HardDriveClass>;
    let comp: HardDriveClass;
    let hardDriveServiceStub: SinonStubbedInstance<HardDriveService>;

    beforeEach(() => {
      hardDriveServiceStub = sinon.createStubInstance<HardDriveService>(HardDriveService);

      wrapper = shallowMount<HardDriveClass>(HardDriveDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { hardDriveService: () => hardDriveServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundHardDrive = { id: 123 };
        hardDriveServiceStub.find.resolves(foundHardDrive);

        // WHEN
        comp.retrieveHardDrive(123);
        await comp.$nextTick();

        // THEN
        expect(comp.hardDrive).toBe(foundHardDrive);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHardDrive = { id: 123 };
        hardDriveServiceStub.find.resolves(foundHardDrive);

        // WHEN
        comp.beforeRouteEnter({ params: { hardDriveId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.hardDrive).toBe(foundHardDrive);
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
