/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ComputerCaseComponent from '@/entities/computer-case/computer-case.vue';
import ComputerCaseClass from '@/entities/computer-case/computer-case.component';
import ComputerCaseService from '@/entities/computer-case/computer-case.service';

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
  describe('ComputerCase Management Component', () => {
    let wrapper: Wrapper<ComputerCaseClass>;
    let comp: ComputerCaseClass;
    let computerCaseServiceStub: SinonStubbedInstance<ComputerCaseService>;

    beforeEach(() => {
      computerCaseServiceStub = sinon.createStubInstance<ComputerCaseService>(ComputerCaseService);
      computerCaseServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ComputerCaseClass>(ComputerCaseComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          computerCaseService: () => computerCaseServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      computerCaseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllComputerCases();
      await comp.$nextTick();

      // THEN
      expect(computerCaseServiceStub.retrieve.called).toBeTruthy();
      expect(comp.computerCases[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      computerCaseServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeComputerCase();
      await comp.$nextTick();

      // THEN
      expect(computerCaseServiceStub.delete.called).toBeTruthy();
      expect(computerCaseServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
