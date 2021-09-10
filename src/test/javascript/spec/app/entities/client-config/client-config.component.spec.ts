/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ClientConfigComponent from '@/entities/client-config/client-config.vue';
import ClientConfigClass from '@/entities/client-config/client-config.component';
import ClientConfigService from '@/entities/client-config/client-config.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ClientConfig Management Component', () => {
    let wrapper: Wrapper<ClientConfigClass>;
    let comp: ClientConfigClass;
    let clientConfigServiceStub: SinonStubbedInstance<ClientConfigService>;

    beforeEach(() => {
      clientConfigServiceStub = sinon.createStubInstance<ClientConfigService>(ClientConfigService);
      clientConfigServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ClientConfigClass>(ClientConfigComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          clientConfigService: () => clientConfigServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      clientConfigServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllClientConfigs();
      await comp.$nextTick();

      // THEN
      expect(clientConfigServiceStub.retrieve.called).toBeTruthy();
      expect(comp.clientConfigs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      clientConfigServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeClientConfig();
      await comp.$nextTick();

      // THEN
      expect(clientConfigServiceStub.delete.called).toBeTruthy();
      expect(clientConfigServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
