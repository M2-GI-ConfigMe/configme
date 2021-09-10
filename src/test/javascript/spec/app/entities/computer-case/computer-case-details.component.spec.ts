/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ComputerCaseDetailComponent from '@/entities/computer-case/computer-case-details.vue';
import ComputerCaseClass from '@/entities/computer-case/computer-case-details.component';
import ComputerCaseService from '@/entities/computer-case/computer-case.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ComputerCase Management Detail Component', () => {
    let wrapper: Wrapper<ComputerCaseClass>;
    let comp: ComputerCaseClass;
    let computerCaseServiceStub: SinonStubbedInstance<ComputerCaseService>;

    beforeEach(() => {
      computerCaseServiceStub = sinon.createStubInstance<ComputerCaseService>(ComputerCaseService);

      wrapper = shallowMount<ComputerCaseClass>(ComputerCaseDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { computerCaseService: () => computerCaseServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundComputerCase = { id: 123 };
        computerCaseServiceStub.find.resolves(foundComputerCase);

        // WHEN
        comp.retrieveComputerCase(123);
        await comp.$nextTick();

        // THEN
        expect(comp.computerCase).toBe(foundComputerCase);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundComputerCase = { id: 123 };
        computerCaseServiceStub.find.resolves(foundComputerCase);

        // WHEN
        comp.beforeRouteEnter({ params: { computerCaseId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.computerCase).toBe(foundComputerCase);
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
