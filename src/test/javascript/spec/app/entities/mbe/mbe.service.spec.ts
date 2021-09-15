/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import MbeService from '@/entities/mbe/mbe.service';
import { Mbe } from '@/shared/model/mbe.model';
import { SocketType } from '@/shared/model/enumerations/socket-type.model';
import { RamType } from '@/shared/model/enumerations/ram-type.model';
import { FormatType } from '@/shared/model/enumerations/format-type.model';

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
  describe('Mbe Service', () => {
    let service: MbeService;
    let elemDefault;

    beforeEach(() => {
      service = new MbeService();
      elemDefault = new Mbe(
        123,
        'AAAAAA',
        0.5,
        0.3,
        27,
        'fsdf',
        'INTEL',
        true,
        SocketType.AM4,
        RamType.DDR3,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        FormatType.ATX
      );
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

      it('should create a Mbe', async () => {
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

      it('should not create a Mbe', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Mbe', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            discount: 0.3,
            stock: 27,
            img: 'fsdf',
            brand: 'INTEL',
            isActive: false,
            socketCpu: 'BBBBBB',
            ramType: 'BBBBBB',
            ramFrequencyMax: 1,
            ramSizeMax: 1,
            pciOutputs: 'BBBBBB',
            displayOutput: 'BBBBBB',
            storageOutput: 'BBBBBB',
            insideIO: 'BBBBBB',
            backPanelOutput: 'BBBBBB',
            bios: 'BBBBBB',
            format: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Mbe', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Mbe', async () => {
        const patchObject = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            stock: 27,
            brand: 'INTEL',
            ramSizeMax: 1,
            displayOutput: 'BBBBBB',
            storageOutput: 'BBBBBB',
            insideIO: 'BBBBBB',
            format: 'BBBBBB',
          },
          new Mbe()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Mbe', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Mbe', async () => {
        const returnedFromService = Object.assign(
          {
            socketCpu: 'BBBBBB',
            ramType: 'BBBBBB',
            ramFrequencyMax: 1,
            ramSizeMax: 1,
            pciOutputs: 'BBBBBB',
            displayOutput: 'BBBBBB',
            storageOutput: 'BBBBBB',
            insideIO: 'BBBBBB',
            backPanelOutput: 'BBBBBB',
            bios: 'BBBBBB',
            format: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Mbe', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Mbe', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Mbe', async () => {
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
