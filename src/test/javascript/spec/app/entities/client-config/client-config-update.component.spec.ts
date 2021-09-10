/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ClientConfigUpdateComponent from '@/entities/client-config/client-config-update.vue';
import ClientConfigClass from '@/entities/client-config/client-config-update.component';
import ClientConfigService from '@/entities/client-config/client-config.service';

import CpuService from '@/entities/cpu/cpu.service';

import GpuService from '@/entities/gpu/gpu.service';

import PsuService from '@/entities/psu/psu.service';

import VentiradService from '@/entities/ventirad/ventirad.service';

import MbeService from '@/entities/mbe/mbe.service';

import ComputerCaseService from '@/entities/computer-case/computer-case.service';

import HardDriveService from '@/entities/hard-drive/hard-drive.service';

import RamService from '@/entities/ram/ram.service';

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
  describe('ClientConfig Management Update Component', () => {
    let wrapper: Wrapper<ClientConfigClass>;
    let comp: ClientConfigClass;
    let clientConfigServiceStub: SinonStubbedInstance<ClientConfigService>;

    beforeEach(() => {
      clientConfigServiceStub = sinon.createStubInstance<ClientConfigService>(ClientConfigService);

      wrapper = shallowMount<ClientConfigClass>(ClientConfigUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          clientConfigService: () => clientConfigServiceStub,

          cpuService: () => new CpuService(),

          gpuService: () => new GpuService(),

          psuService: () => new PsuService(),

          ventiradService: () => new VentiradService(),

          mbeService: () => new MbeService(),

          computerCaseService: () => new ComputerCaseService(),

          hardDriveService: () => new HardDriveService(),

          ramService: () => new RamService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.clientConfig = entity;
        clientConfigServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientConfigServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.clientConfig = entity;
        clientConfigServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientConfigServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundClientConfig = { id: 123 };
        clientConfigServiceStub.find.resolves(foundClientConfig);
        clientConfigServiceStub.retrieve.resolves([foundClientConfig]);

        // WHEN
        comp.beforeRouteEnter({ params: { clientConfigId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.clientConfig).toBe(foundClientConfig);
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
