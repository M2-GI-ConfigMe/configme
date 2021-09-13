/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ComputerCaseUpdateComponent from '@/entities/computer-case/computer-case-update.vue';
import ComputerCaseClass from '@/entities/computer-case/computer-case-update.component';
import ComputerCaseService from '@/entities/computer-case/computer-case.service';

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
  describe('ComputerCase Management Update Component', () => {
    let wrapper: Wrapper<ComputerCaseClass>;
    let comp: ComputerCaseClass;
    let computerCaseServiceStub: SinonStubbedInstance<ComputerCaseService>;

    beforeEach(() => {
      computerCaseServiceStub = sinon.createStubInstance<ComputerCaseService>(ComputerCaseService);

      wrapper = shallowMount<ComputerCaseClass>(ComputerCaseUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          computerCaseService: () => computerCaseServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.computerCase = entity;
        computerCaseServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(computerCaseServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.computerCase = entity;
        computerCaseServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(computerCaseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundComputerCase = { id: 123 };
        computerCaseServiceStub.find.resolves(foundComputerCase);
        computerCaseServiceStub.retrieve.resolves([foundComputerCase]);

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
