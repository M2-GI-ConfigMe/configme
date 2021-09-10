/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ClientConfigDetailComponent from '@/entities/client-config/client-config-details.vue';
import ClientConfigClass from '@/entities/client-config/client-config-details.component';
import ClientConfigService from '@/entities/client-config/client-config.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ClientConfig Management Detail Component', () => {
    let wrapper: Wrapper<ClientConfigClass>;
    let comp: ClientConfigClass;
    let clientConfigServiceStub: SinonStubbedInstance<ClientConfigService>;

    beforeEach(() => {
      clientConfigServiceStub = sinon.createStubInstance<ClientConfigService>(ClientConfigService);

      wrapper = shallowMount<ClientConfigClass>(ClientConfigDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { clientConfigService: () => clientConfigServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundClientConfig = { id: 123 };
        clientConfigServiceStub.find.resolves(foundClientConfig);

        // WHEN
        comp.retrieveClientConfig(123);
        await comp.$nextTick();

        // THEN
        expect(comp.clientConfig).toBe(foundClientConfig);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundClientConfig = { id: 123 };
        clientConfigServiceStub.find.resolves(foundClientConfig);

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
