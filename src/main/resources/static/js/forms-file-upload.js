/**
 * File Upload
 */

'use strict';

(function () {
  // previewTemplate: Updated Dropzone default previewTemplate
  // ! Don't change it unless you really know what you are doing
  const previewTemplate = `<div class="dz-preview dz-file-preview">
<div class="dz-details">
  <div class="dz-thumbnail">
    <img data-dz-thumbnail>
    <span class="dz-nopreview">No preview</span>
    <div class="dz-success-mark"></div>
    <div class="dz-error-mark"></div>
    <div class="dz-error-message"><span data-dz-errormessage></span></div>
    <div class="progress">
      <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuemin="0" aria-valuemax="1000" data-dz-uploadprogress></div>
    </div>
  </div>
  <div class="dz-filename" data-dz-name></div>
  <div class="dz-size" data-dz-size></div>
</div>
</div>`;

  // ? Start your code from here

  // Basic Dropzone
  // --------------------------------------------------------------------

  const myDropzone = new Dropzone('#dropzone-basic', {
    previewTemplate: previewTemplate,
    parallelUploads: 5,
    maxFilesize: 10,
    addRemoveLinks: true,
    maxFiles: 15
  });

  let uploadedFiles = new Map();
  window.uploadedFiles = uploadedFiles;

  myDropzone.on('success', function (file, response) {
    console.log('Файл успешно загружен:',  file.name);
    uploadedFiles.set(file.name, file);
  });
  myDropzone.on('removedfile', function (file) {
    console.log('Файл был удален:', file.name);
    uploadedFiles.delete( file.name);
  });

  myDropzone.on('queuecomplete', function () {
    console.log('Загрузка всех файлов завершена.');
  });

  // Multiple Dropzone
  // --------------------------------------------------------------------
  // const dropzoneMulti = new Dropzone('#dropzone-multi', {
  //   previewTemplate: previewTemplate,
  //   parallelUploads: 1,
  //   maxFilesize: 5,
  //   addRemoveLinks: true
  // });
})();
