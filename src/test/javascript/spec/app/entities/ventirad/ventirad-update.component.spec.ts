/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import VentiradUpdateComponent from '@/entities/ventirad/ventirad-update.vue';
import VentiradClass from '@/entities/ventirad/ventirad-update.component';
import VentiradService from '@/entities/ventirad/ventirad.service';

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
  describe('Ventirad Management Update Component', () => {
    let wrapper: Wrapper<VentiradClass>;
    let comp: VentiradClass;
    let ventiradServiceStub: SinonStubbedInstance<VentiradService>;

    beforeEach(() => {
      ventiradServiceStub = sinon.createStubInstance<VentiradService>(VentiradService);

      wrapper = shallowMount<VentiradClass>(VentiradUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          ventiradService: () => ventiradServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.ventirad = entity;
        ventiradServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ventiradServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.ventirad = entity;
        ventiradServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ventiradServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVentirad = { id: 123 };
        ventiradServiceStub.find.resolves(foundVentirad);
        ventiradServiceStub.retrieve.resolves([foundVentirad]);

        // WHEN
        comp.beforeRouteEnter({ params: { ventiradId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ventirad).toBe(foundVentirad);
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
