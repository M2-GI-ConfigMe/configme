/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PsuUpdateComponent from '@/entities/psu/psu-update.vue';
import PsuClass from '@/entities/psu/psu-update.component';
import PsuService from '@/entities/psu/psu.service';

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
  describe('Psu Management Update Component', () => {
    let wrapper: Wrapper<PsuClass>;
    let comp: PsuClass;
    let psuServiceStub: SinonStubbedInstance<PsuService>;

    beforeEach(() => {
      psuServiceStub = sinon.createStubInstance<PsuService>(PsuService);

      wrapper = shallowMount<PsuClass>(PsuUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          psuService: () => psuServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.psu = entity;
        psuServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(psuServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.psu = entity;
        psuServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(psuServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPsu = { id: 123 };
        psuServiceStub.find.resolves(foundPsu);
        psuServiceStub.retrieve.resolves([foundPsu]);

        // WHEN
        comp.beforeRouteEnter({ params: { psuId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.psu).toBe(foundPsu);
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
