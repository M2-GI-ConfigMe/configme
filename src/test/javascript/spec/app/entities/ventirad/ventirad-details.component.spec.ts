/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VentiradDetailComponent from '@/entities/ventirad/ventirad-details.vue';
import VentiradClass from '@/entities/ventirad/ventirad-details.component';
import VentiradService from '@/entities/ventirad/ventirad.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Ventirad Management Detail Component', () => {
    let wrapper: Wrapper<VentiradClass>;
    let comp: VentiradClass;
    let ventiradServiceStub: SinonStubbedInstance<VentiradService>;

    beforeEach(() => {
      ventiradServiceStub = sinon.createStubInstance<VentiradService>(VentiradService);

      wrapper = shallowMount<VentiradClass>(VentiradDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ventiradService: () => ventiradServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVentirad = { id: 123 };
        ventiradServiceStub.find.resolves(foundVentirad);

        // WHEN
        comp.retrieveVentirad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ventirad).toBe(foundVentirad);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVentirad = { id: 123 };
        ventiradServiceStub.find.resolves(foundVentirad);

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
