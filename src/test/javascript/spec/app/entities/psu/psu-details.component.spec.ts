/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PsuDetailComponent from '@/entities/psu/psu-details.vue';
import PsuClass from '@/entities/psu/psu-details.component';
import PsuService from '@/entities/psu/psu.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Psu Management Detail Component', () => {
    let wrapper: Wrapper<PsuClass>;
    let comp: PsuClass;
    let psuServiceStub: SinonStubbedInstance<PsuService>;

    beforeEach(() => {
      psuServiceStub = sinon.createStubInstance<PsuService>(PsuService);

      wrapper = shallowMount<PsuClass>(PsuDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { psuService: () => psuServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPsu = { id: 123 };
        psuServiceStub.find.resolves(foundPsu);

        // WHEN
        comp.retrievePsu(123);
        await comp.$nextTick();

        // THEN
        expect(comp.psu).toBe(foundPsu);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPsu = { id: 123 };
        psuServiceStub.find.resolves(foundPsu);

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
