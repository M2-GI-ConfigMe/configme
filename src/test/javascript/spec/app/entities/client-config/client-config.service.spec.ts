/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import ClientConfigService from '@/entities/client-config/client-config.service';
import { ClientConfig } from '@/shared/model/client-config.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('ClientConfig Service', () => {
    let service: ClientConfigService;
    let elemDefault;

    beforeEach(() => {
      service = new ClientConfigService();
      elemDefault = new ClientConfig(123, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a ClientConfig', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a ClientConfig', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a ClientConfig', async () => {
        const returnedFromService = Object.assign(
          {
            tags: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            isComlete: true,
            researchKey: 'BBBBBB',
            cpuPrice: 1,
            gpuPrice: 1,
            ram1Price: 1,
            ram2Price: 1,
            psuPrice: 1,
            computerCasePrice: 1,
            ventiradPrice: 1,
            hd1Price: 1,
            hd2Price: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a ClientConfig', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a ClientConfig', async () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            ram1Price: 1,
            ram2Price: 1,
          },
          new ClientConfig()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a ClientConfig', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of ClientConfig', async () => {
        const returnedFromService = Object.assign(
          {
            tags: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            isComlete: true,
            researchKey: 'BBBBBB',
            cpuPrice: 1,
            gpuPrice: 1,
            ram1Price: 1,
            ram2Price: 1,
            psuPrice: 1,
            computerCasePrice: 1,
            ventiradPrice: 1,
            hd1Price: 1,
            hd2Price: 1,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of ClientConfig', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a ClientConfig', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a ClientConfig', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
