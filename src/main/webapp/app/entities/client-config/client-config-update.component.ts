import { Component, Vue, Inject } from 'vue-property-decorator';

import CpuService from '@/entities/cpu/cpu.service';
import { ICpu } from '@/shared/model/cpu.model';

import GpuService from '@/entities/gpu/gpu.service';
import { IGpu } from '@/shared/model/gpu.model';

import PsuService from '@/entities/psu/psu.service';
import { IPsu } from '@/shared/model/psu.model';

import VentiradService from '@/entities/ventirad/ventirad.service';
import { IVentirad } from '@/shared/model/ventirad.model';

import MbeService from '@/entities/mbe/mbe.service';
import { IMbe } from '@/shared/model/mbe.model';

import ComputerCaseService from '@/entities/computer-case/computer-case.service';
import { IComputerCase } from '@/shared/model/computer-case.model';

import HardDriveService from '@/entities/hard-drive/hard-drive.service';
import { IHardDrive } from '@/shared/model/hard-drive.model';

import RamService from '@/entities/ram/ram.service';
import { IRam } from '@/shared/model/ram.model';

import { IClientConfig, ClientConfig } from '@/shared/model/client-config.model';
import ClientConfigService from './client-config.service';

const validations: any = {
  clientConfig: {
    tags: {},
    name: {},
    description: {},
    isComlete: {},
    researchKey: {},
    cpuPrice: {},
    gpuPrice: {},
    ram1Price: {},
    ram2Price: {},
    psuPrice: {},
    computerCasePrice: {},
    ventiradPrice: {},
    hd1Price: {},
    hd2Price: {},
  },
};

@Component({
  validations,
})
export default class ClientConfigUpdate extends Vue {
  @Inject('clientConfigService') private clientConfigService: () => ClientConfigService;
  public clientConfig: IClientConfig = new ClientConfig();

  @Inject('cpuService') private cpuService: () => CpuService;

  public cpus: ICpu[] = [];

  @Inject('gpuService') private gpuService: () => GpuService;

  public gpus: IGpu[] = [];

  @Inject('psuService') private psuService: () => PsuService;

  public psus: IPsu[] = [];

  @Inject('ventiradService') private ventiradService: () => VentiradService;

  public ventirads: IVentirad[] = [];

  @Inject('mbeService') private mbeService: () => MbeService;

  public mbes: IMbe[] = [];

  @Inject('computerCaseService') private computerCaseService: () => ComputerCaseService;

  public computerCases: IComputerCase[] = [];

  @Inject('hardDriveService') private hardDriveService: () => HardDriveService;

  public hardDrives: IHardDrive[] = [];

  @Inject('ramService') private ramService: () => RamService;

  public rams: IRam[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientConfigId) {
        vm.retrieveClientConfig(to.params.clientConfigId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.clientConfig.id) {
      this.clientConfigService()
        .update(this.clientConfig)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.clientConfig.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.clientConfigService()
        .create(this.clientConfig)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.clientConfig.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveClientConfig(clientConfigId): void {
    this.clientConfigService()
      .find(clientConfigId)
      .then(res => {
        this.clientConfig = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cpuService()
      .retrieve()
      .then(res => {
        this.cpus = res.data.content;
      });
    this.gpuService()
      .retrieve()
      .then(res => {
        this.gpus = res.data.content;
      });
    this.psuService()
      .retrieve()
      .then(res => {
        this.psus = res.data.content;
      });
    this.ventiradService()
      .retrieve()
      .then(res => {
        this.ventirads = res.data.content;
      });
    this.mbeService()
      .retrieve()
      .then(res => {
        this.mbes = res.data.content;
      });
    this.computerCaseService()
      .retrieve()
      .then(res => {
        this.computerCases = res.data.content;
      });
    this.hardDriveService()
      .retrieve()
      .then(res => {
        this.hardDrives = res.data.content;
      });
    this.ramService()
      .retrieve()
      .then(res => {
        this.rams = res.data.content;
      });
  }
}
