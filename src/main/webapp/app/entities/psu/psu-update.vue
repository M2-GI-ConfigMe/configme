<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.psu.home.createOrEditLabel"
          data-cy="PsuCreateUpdateHeading"
          v-text="$t('configmeApp.psu.home.createOrEditLabel')"
        >
          Create or edit a Psu
        </h2>
        <div>
          <div class="form-group" v-if="psu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="psu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.power')" for="psu-power">Power</label>
            <input
              type="number"
              class="form-control"
              name="power"
              id="psu-power"
              data-cy="power"
              :class="{ valid: !$v.psu.power.$invalid, invalid: $v.psu.power.$invalid }"
              v-model.number="$v.psu.power.$model"
              required
            />
            <div v-if="$v.psu.power.$anyDirty && $v.psu.power.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.power.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.power.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.certification')" for="psu-certification">Certification</label>
            <input
              type="text"
              class="form-control"
              name="certification"
              id="psu-certification"
              data-cy="certification"
              :class="{ valid: !$v.psu.certification.$invalid, invalid: $v.psu.certification.$invalid }"
              v-model="$v.psu.certification.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.modularity')" for="psu-modularity">Modularity</label>
            <select
              class="form-control"
              name="modularity"
              :class="{ valid: !$v.psu.modularity.$invalid, invalid: $v.psu.modularity.$invalid }"
              v-model="$v.psu.modularity.$model"
              id="psu-modularity"
              data-cy="modularity"
              required
            >
              <option value="NON_MODULAIRE" v-bind:label="$t('configmeApp.ModularityType.NON_MODULAIRE')">NON_MODULAIRE</option>
              <option value="SEMI_MODULAIRE" v-bind:label="$t('configmeApp.ModularityType.SEMI_MODULAIRE')">SEMI_MODULAIRE</option>
              <option value="MODULAIRE" v-bind:label="$t('configmeApp.ModularityType.MODULAIRE')">MODULAIRE</option>
            </select>
            <div v-if="$v.psu.modularity.$anyDirty && $v.psu.modularity.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.modularity.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.nbSata')" for="psu-nbSata">Nb Sata</label>
            <input
              type="number"
              class="form-control"
              name="nbSata"
              id="psu-nbSata"
              data-cy="nbSata"
              :class="{ valid: !$v.psu.nbSata.$invalid, invalid: $v.psu.nbSata.$invalid }"
              v-model.number="$v.psu.nbSata.$model"
              required
            />
            <div v-if="$v.psu.nbSata.$anyDirty && $v.psu.nbSata.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.nbSata.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbSata.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbSata.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.nbPciE')" for="psu-nbPciE">Nb Pci E</label>
            <input
              type="number"
              class="form-control"
              name="nbPciE"
              id="psu-nbPciE"
              data-cy="nbPciE"
              :class="{ valid: !$v.psu.nbPciE.$invalid, invalid: $v.psu.nbPciE.$invalid }"
              v-model.number="$v.psu.nbPciE.$model"
              required
            />
            <div v-if="$v.psu.nbPciE.$anyDirty && $v.psu.nbPciE.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.nbPciE.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbPciE.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbPciE.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.outputs')" for="psu-outputs">Outputs</label>
            <input
              type="text"
              class="form-control"
              name="outputs"
              id="psu-outputs"
              data-cy="outputs"
              :class="{ valid: !$v.psu.outputs.$invalid, invalid: $v.psu.outputs.$invalid }"
              v-model="$v.psu.outputs.$model"
              required
            />
            <div v-if="$v.psu.outputs.$anyDirty && $v.psu.outputs.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.outputs.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.psu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./psu-update.component.ts"></script>
