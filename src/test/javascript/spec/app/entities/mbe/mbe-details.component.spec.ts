/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MbeDetailComponent from '@/entities/mbe/mbe-details.vue';
import MbeClass from '@/entities/mbe/mbe-details.component';
import MbeService from '@/entities/mbe/mbe.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Mbe Management Detail Component', () => {
    let wrapper: Wrapper<MbeClass>;
    let comp: MbeClass;
    let mbeServiceStub: SinonStubbedInstance<MbeService>;

    beforeEach(() => {
      mbeServiceStub = sinon.createStubInstance<MbeService>(MbeService);

      wrapper = shallowMount<MbeClass>(MbeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { mbeService: () => mbeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMbe = { id: 123 };
        mbeServiceStub.find.resolves(foundMbe);

        // WHEN
        comp.retrieveMbe(123);
        await comp.$nextTick();

        // THEN
        expect(comp.mbe).toBe(foundMbe);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMbe = { id: 123 };
        mbeServiceStub.find.resolves(foundMbe);

        // WHEN
        comp.beforeRouteEnter({ params: { mbeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.mbe).toBe(foundMbe);
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
